package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private AccountResponseMapper accountResponseMapper;
    private AccountRequestMapper accountRequestMapper;
    private final RestTemplate restTemplate;

    @Value("${auth0.domain}")
    private String domain;

    @Value("${spring.security.oauth2.resourceserver.jwt.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.jwt.client-secret}")
    private String clientSecret;


    private JavaMailSender javaMailSender;

    public AccountServiceImpl(JavaMailSender javaMailSender, AccountRepository accountRepository, AccountResponseMapper accountResponseMapper, AccountRequestMapper accountRequestMapper, RestTemplate restTemplate) {
        this.accountRepository = accountRepository;
        this.accountResponseMapper = accountResponseMapper;
        this.accountRequestMapper = accountRequestMapper;
        this.javaMailSender = javaMailSender;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<AccountResponseModel> getAllAccounts(String accountId, String username, String email, String city) {
        Set<Account> filteredAccounts = new HashSet<>();

        if (accountId != null) {
            filteredAccounts.addAll(accountRepository.findAllAccountsByAccountIdentifier_AccountIdStartingWith(accountId));
        } else if (username != null) {
            filteredAccounts.addAll(accountRepository.findAllAccountByUsernameStartingWith(username));
        } else if (email != null) {
            filteredAccounts.addAll(accountRepository.findAllAccountByEmailStartingWith(email));
        } else if (city != null) {
            filteredAccounts.addAll(accountRepository.findAllAccountByCityStartingWith(city));
        } else {
            filteredAccounts.addAll(accountRepository.findAll());
        }

        return accountResponseMapper.entityListToResponseModelList(filteredAccounts.stream().sorted(Comparator.comparing(account -> account.getAccountIdentifier().getAccountId())).toList());
    }


    @Override
    public AccountResponseModel getAccountByAccountId(String accountId) {
        return accountResponseMapper.entityToResponseModel(accountRepository.findAccountByAccountIdentifier_AccountId(accountId));
    }

    @Override
    public AccountResponseModel getByUserId(String userId) {
        Account account = accountRepository.findAccountByUserId(userId);
        return accountResponseMapper.entityToResponseModel(account);
    }
    @Override
    public AccountResponseModel addAccount(AccountRequestModel accountRequestModel) {
        Account account = accountRequestMapper.requestModelToEntity(accountRequestModel);
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        account.setDateCreated(date);
        Account saved = accountRepository.save(account);
        sendWelcomeEmail(account);
        return accountResponseMapper.entityToResponseModel(saved);
    }

    @Override
    public AccountResponseModel updateAccount(AccountRequestModel accountRequestModel, String userId) {
        Account existingAccount = accountRepository.findAccountByUserId((userId));
        if(existingAccount == null){
            return  null;
        }
        Account account = accountRequestMapper.requestModelToEntity(accountRequestModel);
        account.setId(existingAccount.getId());
        account.setAccountIdentifier(existingAccount.getAccountIdentifier());
        account.setUserId(existingAccount.getUserId());

        boolean emailUpdateSuccess = true;
        boolean usernameUpdateSuccess = true;

        if (accountRequestModel.getEmail() != null && !accountRequestModel.getEmail().isEmpty()) {
            Map<String, Object> emailUpdate = new HashMap<>();
            emailUpdate.put("email", accountRequestModel.getEmail());
            emailUpdateSuccess = updateAuth0UserInformation(userId, emailUpdate);
        }

        if (accountRequestModel.getUsername() != null && !accountRequestModel.getUsername().isEmpty()) {
            Map<String, Object> usernameUpdate = new HashMap<>();
            usernameUpdate.put("username", accountRequestModel.getUsername());
            if (emailUpdateSuccess) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            usernameUpdateSuccess = updateAuth0UserInformation(userId, usernameUpdate);
        }

        if (!emailUpdateSuccess || !usernameUpdateSuccess) {
            return null;
        }
        return accountResponseMapper.entityToResponseModel(accountRepository.save(account));
    }

    public String getManagementApiToken() {
        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = "https://" + domain + "/oauth/token";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("client_id", clientId);
        requestBody.put("client_secret", clientSecret);
        requestBody.put("audience", "https://" + domain + "/api/v2/");
        requestBody.put("grant_type", "client_credentials");
        requestBody.put("scope", "read:users update:users");

        Map<String, String> response = restTemplate.postForObject(tokenUrl, requestBody, Map.class);
        return response.get("access_token");
    }

    public boolean updateAuth0UserInformation(String userId, Map<String, Object> updates) {
        String token = getManagementApiToken();
        if (token == null) {
            return false;
        }

        String updateUserUrl = "https://" + domain + "/api/v2/users/auth0|" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(updates, headers);

        try {
            restTemplate.exchange(updateUserUrl, HttpMethod.PATCH, entity, String.class);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }


    @Override
    public void removeAccount(String accountId) {

    }

    private void sendWelcomeEmail(Account account) {
        try {
            String template = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html dir=\"ltr\" lang=\"en\">\n" +
                    "\n" +
                    "  <head>\n" +
                    "    <meta content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\" />\n" +
                    "  </head>\n" +
                    "  <div style=\"display:none;overflow:hidden;line-height:1px;opacity:0;max-height:0;max-width:0\">Welcome, {username}!<div> \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF</div>\n" +
                    "  </div>\n" +
                    "\n" +
                    "  <body style=\"background-color:rgb(250,251,251);font-size:1rem;line-height:1.5rem;font-family:ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;\"><img alt=\"FitWSarah\" height=\"75\" src=\"https://i.imgur.com/ndtF9i4.png\" style=\"display:block;outline:none;border:none;text-decoration:none;margin-left:auto;margin-right:auto;margin-top:20px;margin-bottom:20px\" width=\"184\" />\n" +
                    "    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"max-width:37.5em;background-color:rgb(255,255,255);padding:45px\">\n" +
                    "      <tbody>\n" +
                    "        <tr style=\"width:100%\">\n" +
                    "          <td>\n" +
                    "            <h1 class=\"\" style=\"text-align:center;margin-top:0px;margin-bottom:0px;line-height:2rem\">Welcome to FitWSarah</h1>\n" +
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "              <tbody>\n" +
                    "                <tr>\n" +
                    "                  <td>\n" +
                    "                    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "                      <tbody style=\"width:100%\">\n" +
                    "                        <tr style=\"width:100%\">\n" +
                    "                          <p style=\"font-size:1rem;line-height:1.5rem;margin:16px 0\">Thank you for joining us in our fitness journey</p>\n" +
                    "                          <p style=\"font-size:1rem;line-height:1.5rem;margin:16px 0\">Here&#x27;s how to get started:</p>\n" +
                    "                        </tr>\n" +
                    "                      </tbody>\n" +
                    "                    </table>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "            <ul>\n" +
                    "              <li style=\"margin-bottom:20px\"><strong>Choose a fitness service.</strong> Browse fitness services in the Home Page and check their details for more information.</li>\n" +
                    "              <li style=\"margin-bottom:20px\"><strong>Book an appointment.</strong> Book a personal fitness session via the home page from all my current availabilities.</li>\n" +
                    "              <li style=\"margin-bottom:20px\"><strong>Check your profile.</strong> Quickly manage your current appointments, invoices, coach notes and much more.</li>\n" +
                    "              <li style=\"margin-bottom:20px\"><strong>Learn more about fitness.</strong> Get feedback from me in the form of coach notes that will help you throughout your fitness journey.</li>\n" +
                    "            </ul>\n" +
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"text-align:center\">\n" +
                    "              <tbody>\n" +
                    "                <tr>\n" +
                    "                  <td><a style=\"background-color:rgb(34,80,244);color:rgb(255,255,255);border-radius:0.5rem;padding-top:0.75rem;padding-bottom:0.75rem;padding-left:18px;padding-right:18px;line-height:100%;text-decoration:none;display:inline-block;max-width:100%;padding:12px 18px 12px 18px\" href=\"https://fitwsarah-tm3c.onrender.com/\"><span><!--[if mso]><i style=\"letter-spacing: 18px;mso-font-width:-100%;mso-text-raise:18\" hidden>&nbsp;</i><![endif]--></span><span style=\"max-width:100%;display:inline-block;line-height:120%;mso-padding-alt:0px;mso-text-raise:9px\">Start Booking</span><span><!--[if mso]><i style=\"letter-spacing: 18px;mso-font-width:-100%\" hidden>&nbsp;</i><![endif]--></span></a></td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"margin-top:45px\">\n" +
                    "              <tbody>\n" +
                    "                <tr>\n" +
                    "                  <td>\n" +
                    "                    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "                      <tbody style=\"width:100%\">\n" +
                    "                        <tr style=\"width:100%\">\n" +
                    "                          </tr>\n" +
                    "                      </tbody>\n" +
                    "                    </table>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "          </td>\n" +
                    "        </tr>\n" +
                    "      </tbody>\n" +
                    "    </table>\n" +
                    "    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"max-width:37.5em;margin-top:20px\">\n" +
                    "      <tbody>\n" +
                    "        <tr style=\"width:100%\">\n" +
                    "          <td>\n" +
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "              <tbody>\n" +
                    "                <tr>\n" +
                    "                  <td>\n" +
                    "                    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "                      <tbody style=\"width:100%\">\n" +
                    "               <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "              <tbody style=\"width:100%\">\n" +
                    "                <tr style=\"width:100%\">\n" +
                    "                  <td align=\"right\" data-id=\"__react-email-column\" style=\"width:50%;padding-right:8px\"><img src=\"https://react-email-demo-7s5r0trkn-resend.vercel.app/static/twitch-icon-twitter.png\" style=\"display:block;outline:none;border:none;text-decoration:none\" /></td>\n" +
                    "                  <td align=\"left\" data-id=\"__react-email-column\" style=\"width:50%;padding-left:8px\"><img src=\"https://react-email-demo-7s5r0trkn-resend.vercel.app/static/twitch-icon-facebook.png\" style=\"display:block;outline:none;border:none;text-decoration:none\" /></td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "              <tbody style=\"width:100%\">\n" +
                    "                <tr style=\"width:100%\">\n" +
                    "                  <p style=\"font-size:14px;line-height:24px;margin:16px 0;text-align:center;color:#706a7b\">©Copyright 2023 All rights reserved.<br />Powered by TheMontrealGoats</p>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "                      </tbody>\n" +
                    "                    </table>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "            <p style=\"font-size:14px;line-height:24px;margin:16px 0;text-align:center;color:rgb(156,163,175);margin-bottom:45px\">FitWSarah, Brossard QC, CA</p>\n" +
                    "          </td>\n" +
                    "        </tr>\n" +
                    "      </tbody>\n" +
                    "    </table>\n" +
                    "  </body>\n" +
                    "\n" +
                    "</html>";

            String emailContent = template.replace("{username}", account.getUsername());

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);


            helper.setFrom("fitwithsarahfitness@gmail.com");
            helper.setTo(account.getEmail());
            helper.setSubject("Welcome to FitWSarah");
            helper.setText(emailContent, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
