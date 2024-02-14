package com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentRepository;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentRequestMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository appointmentRepository;

    private AppointmentResponseMapper appointmentResponseMapper;

    private AppointmentRequestMapper appointmentRequestMapper;

    private final AccountRepository accountRepository;

    private final FitnessPackageRepository fitnessPackageRepository;
    private JavaMailSender javaMailSender;

    public AppointmentServiceImpl(JavaMailSender javaMailSender, AppointmentRepository appointmentRepository, FitnessPackageRepository fitnessPackageRepository, AppointmentResponseMapper appointmentResponseMapper, AppointmentRequestMapper appointmentRequestMapper, AccountRepository accountRepository){
        this.appointmentRepository = appointmentRepository;
        this.appointmentResponseMapper = appointmentResponseMapper;
        this.appointmentRequestMapper = appointmentRequestMapper;
        this.accountRepository = accountRepository;
        this.javaMailSender = javaMailSender;
        this.fitnessPackageRepository = fitnessPackageRepository;
    }

    @Override
    public List<AppointmentResponseModel> getAllAppointments(String appointmentId, String accountId, String status){
        Set<Appointment> appointments = new HashSet<>();

        if(appointmentId != null){
            appointments.addAll(appointmentRepository.findAllAppointmentsByAppointmentIdentifier_AppointmentIdStartingWith(appointmentId));
        } else if(accountId != null){
            appointments.addAll(appointmentRepository.findAllAppointmentsByUserIdStartingWith(accountId));
        } else if(status != null){
            appointments.addAll(appointmentRepository.findAllAppointmentsByStatus(Status.valueOf(status)));
        } else {
            appointments.addAll(appointmentRepository.findAll());
        }

        return appointmentResponseMapper.entityListToResponseModelList(appointments.stream().sorted(Comparator.comparing(appointment -> appointment.getAppointmentIdentifier().getAppointmentId())).toList());
    }

    @Override
    public List<AppointmentResponseModel> getAllAppointmentsByUserId(String userId) {
        return appointmentResponseMapper.entityListToResponseModelList(appointmentRepository.findAllAppointmentsByUserId(userId));
    }

    @Override
    public List<AppointmentResponseModel> getAllAppointmentsByUserIdAndStatus(String userId, String status) {
        return appointmentResponseMapper.entityListToResponseModelList(appointmentRepository.findAllAppointmentByUserIdAndStatus(userId, Status.valueOf(status)));
    }

    @Override
    public AppointmentResponseModel getAppointmentByAppointmentId(String appointmentId) {
        return appointmentResponseMapper.entityToResponseModel(appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId));
    }

    @Override
    public AppointmentResponseModel addAppointment(AppointmentRequestModel appointmentRequestModel) {
        Appointment appointment = appointmentRequestMapper.requestModelToEntity(appointmentRequestModel);
        appointment.setStatus(Status.REQUESTED);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        sendConfirmationEmail(appointment);
        return appointmentResponseMapper.entityToResponseModel(savedAppointment);
    }

    @Override
    public AppointmentResponseModel updateAppointmentDetails(AppointmentRequestModel appointmentRequestModel, String appointmentId) {
        Appointment appointment = appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId);

        // Check if the appointment exists
        if (appointment == null) {
            throw new EntityNotFoundException("Appointment with ID " + appointmentId + " not found");
        }

        // Update appointment details
        appointment.setStatus(appointmentRequestModel.getStatus());
        appointment.setLocation(appointmentRequestModel.getLocation());
        appointment.setFirstName(appointmentRequestModel.getFirstName());
        appointment.setLastName(appointmentRequestModel.getLastName());
        appointment.setPhoneNum(appointmentRequestModel.getPhoneNum());
        appointment.setDate(appointmentRequestModel.getDate());
        appointment.setTime(appointmentRequestModel.getTime());

        // Save the updated appointment
        appointmentRepository.save(appointment);

        // Return the updated appointment details
        return appointmentResponseMapper.entityToResponseModel(appointment);
    }


    @Override
    public AppointmentResponseModel updateAppointmentStatus(String appointmentId, String status) {
        Appointment appointment = appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId);
        appointment.setStatus(Status.valueOf(Status.CANCELLED.toString()));
        appointmentRepository.save(appointment);
        sendCancellationEmail(appointment);
        return appointmentResponseMapper.entityToResponseModel(appointment);
    }

    @Override
    public AppointmentResponseModel handleAppointmentRequest(String appointmentId, String status) {
        Appointment appointment = appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId);
        appointment.setStatus(Status.valueOf(Status.SCHEDULED.toString()));
        appointmentRepository.save(appointment);
        return appointmentResponseMapper.entityToResponseModel(appointment);
    }

    @Override
    public AppointmentResponseModel updateAppointmentDateTime(String appointmentId, AppointmentRequestModel appointmentRequestModel) {
        Appointment appointment = appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId);
        appointment.setId(appointment.getId());
        appointment.setAvailabilityId(appointmentRequestModel.getAvailabilityId());
        appointment.setUserId(appointmentRequestModel.getUserId());
        appointment.setServiceId(appointmentRequestModel.getServiceId());
        appointment.setLocation(appointmentRequestModel.getLocation());
        appointment.setFirstName(appointmentRequestModel.getFirstName());
        appointment.setLastName(appointmentRequestModel.getLastName());
        appointment.setPhoneNum(appointmentRequestModel.getPhoneNum());
        appointment.setDate(appointmentRequestModel.getDate());
        appointment.setTime(appointmentRequestModel.getTime());
        appointment.setStatus(Status.valueOf(Status.REQUESTED.toString()));
        appointmentRepository.save(appointment);
        return appointmentResponseMapper.entityToResponseModel(appointment);
    }


    @Override
    public void removeAppointment(String appointmentId) {

    }


    private void sendCancellationEmail(Appointment appointment) {
        try {
            String userId = appointment.getUserId();
            Account account = accountRepository.findAccountByUserId(userId);

            String template = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html dir=\"ltr\" lang=\"en\">\n" +
                    "\n" +
                    "  <head>\n" +
                    "    <meta content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\" />\n" +
                    "  </head>\n" +
                    "  <div style=\"display:none;overflow:hidden;line-height:1px;opacity:0;max-height:0;max-width:0\">Your appointment has been cancelled<div> \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF</div>\n" +
                    "  </div>\n" +
                    "\n" +
                    "  <body style=\"background-color:#efeef1;font-family:HelveticaNeue,Helvetica,Arial,sans-serif\">\n" +
                    "    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"max-width:580px;margin:30px auto;background-color:#ffffff\">\n" +
                    "      <tbody>\n" +
                    "        <tr style=\"width:100%\">\n" +
                    "          <td>\n" +
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"display:flex;justify-content:center;aling-items:center;padding:30px\">\n" +
                    "              <tbody>\n" +
                    "                <tr>\n" +
                    "                  <td><img src=\"https://i.imgur.com/ndtF9i4.png\" style=\"display:block;outline:none;border:none;text-decoration:none\" width=\"114\" /></td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"width:100%;display:flex\">\n" +
                    "              <tbody>\n" +
                    "                <tr>\n" +
                    "                  <td>\n" +
                    "                    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "                      <tbody style=\"width:100%\">\n" +
                    "                        <tr style=\"width:100%\">\n" +
                    "                          <td data-id=\"__react-email-column\" style=\"border-bottom:1px solid rgb(238,238,238);width:249px\"></td>\n" +
                    "                          <td data-id=\"__react-email-column\" style=\"border-bottom:1px solid rgb(83,55,71);width:102px\"></td>\n" +
                    "                          <td data-id=\"__react-email-column\" style=\"border-bottom:1px solid rgb(238,238,238);width:249px\"></td>\n" +
                    "                        </tr>\n" +
                    "                      </tbody>\n" +
                    "                    </table>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"padding:5px 20px 10px 20px\">\n" +
                    "              <tbody>\n" +
                    "                <tr>\n" +
                    "                  <td>\n" +
                    "                    <p style=\"font-size:14px;line-height:1.5;margin:16px 0\">Hi <!-- -->{username}<!-- -->,</p>\n" +
                    "                    <p style=\"font-size:14px;line-height:1.5;margin:16px 0\">Unfortunately, the appointment set on<!-- --> <!-- -->{date}, {time}<!-- -->. has been cancelled.</p>\n" +
                    "                    <p style=\"font-size:14px;line-height:1.5;margin:16px 0\">For more information? Please contact<!-- --> <a href=\"#\" style=\"color:#067df7;text-decoration:underline\" target=\"_blank\">me</a></p>\n" +
                    "                    <p style=\"font-size:14px;line-height:1.5;margin:16px 0\">Thank you for your understanding,<br />FitWSarah</p>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "          </td>\n" +
                    "        </tr>\n" +
                    "      </tbody>\n" +
                    "    </table>\n" +
                    "    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"max-width:580px;margin:0 auto\">\n" +
                    "      <tbody>\n" +
                    "        <tr>\n" +
                    "          <td>\n" +
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
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
                    "          </td>\n" +
                    "        </tr>\n" +
                    "      </tbody>\n" +
                    "    </table>\n" +
                    "  </body>\n" +
                    "\n" +
                    "</html>";

            String emailContent = template.replace("{date}", appointment.getDate())
                    .replace("{time}", appointment.getTime())
                    .replace("{username}", account.getUsername());

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("fitwithsarahfitness@gmail.com");
            helper.setTo(account.getEmail());
            helper.setSubject("Appointment Cancellation");
            helper.setText(emailContent, true);


            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendConfirmationEmail(Appointment appointment) {
        try {
            String userId = appointment.getUserId();
            Account account = accountRepository.findAccountByUserId(userId);
            FitnessPackage fitnessPackage = fitnessPackageRepository.findByFitnessPackageIdentifier_ServiceId(appointment.getServiceId());
            String template = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html dir=\"ltr\" lang=\"en\">\n" +
                    "\n" +
                    "  <head>\n" +
                    "    <meta content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\" />\n" +
                    "  </head>\n" +
                    "  <div style=\"display:none;overflow:hidden;line-height:1px;opacity:0;max-height:0;max-width:0\">FitWSarah Appointment Confirmation<div> \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF \u200C\u200B\u200D\u200E\u200F\uFEFF</div>\n" +
                    "  </div>\n" +
                    "\n" +
                    "  <body style=\"background-color:#efeef1;font-family:HelveticaNeue,Helvetica,Arial,sans-serif\">\n" +
                    "    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"max-width:580px;margin:30px auto;background-color:#ffffff\">\n" +
                    "      <tbody>\n" +
                    "        <tr style=\"width:100%\">\n" +
                    "          <td>\n" +
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"display:flex;justify-content:center;aling-items:center;padding:30px\">\n" +
                    "              <tbody>\n" +
                    "                <tr>\n" +
                    "                  <td><img src=\"https://i.imgur.com/ndtF9i4.png\" style=\"display:block;outline:none;border:none;text-decoration:none\" width=\"114\" /></td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"width:100%;display:flex\">\n" +
                    "              <tbody>\n" +
                    "                <tr>\n" +
                    "                  <td>\n" +
                    "                    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "                      <tbody style=\"width:100%\">\n" +
                    "                        <tr style=\"width:100%\">\n" +
                    "                          <td data-id=\"__react-email-column\" style=\"border-bottom:1px solid rgb(238,238,238);width:249px\"></td>\n" +
                    "                          <td data-id=\"__react-email-column\" style=\"border-bottom:1px solid rgb(83,55,71);width:102px\"></td>\n" +
                    "                          <td data-id=\"__react-email-column\" style=\"border-bottom:1px solid rgb(238,238,238);width:249px\"></td>\n" +
                    "                        </tr>\n" +
                    "                      </tbody>\n" +
                    "                    </table>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "<table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px;color:rgb(51,51,51);background-color:rgb(250,250,250);border-radius:3px;font-size:12px\">\n" +
                    "              <tbody>\n" +
                    "                <tr>\n" +
                    "                  <td>\n" +
                    "                    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"height:46px\">\n" +
                    "                      <tbody style=\"width:100%\">\n" +
                    "                        <tr style=\"width:100%\">\n" +
                    "                          <td colSpan=\"2\" data-id=\"__react-email-column\">\n" +
                    "                            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "                              <tbody>\n" +
                    "                                <tr>\n" +
                    "                                  <td>\n" +
                    "                                    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "                                      <tbody style=\"width:100%\">\n" +
                    "                                        <tr style=\"width:100%\">\n" +
                    "                                          <td data-id=\"__react-email-column\" style=\"padding-left:20px;border-style:solid;border-color:white;border-width:0px 1px 1px 0px;height:44px\">\n" +
                    "                                            <p style=\"font-size:10px;line-height:1.4;margin:0;padding:0;color:rgb(102,102,102)\">Email</p><a style=\"color:#15c;text-decoration:underline;font-size:12px;margin:0;padding:0;line-height:1.4\" target=\"_blank\">{email}</a>\n" +
                    "                                          </td>\n" +
                    "                                        </tr>\n" +
                    "                                      </tbody>\n" +
                    "                                    </table>\n" +
                    "                                    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "                                      <tbody style=\"width:100%\">\n" +
                    "                                        <tr style=\"width:100%\">\n" +
                    "                                          <td data-id=\"__react-email-column\" style=\"padding-left:20px;border-style:solid;border-color:white;border-width:0px 1px 1px 0px;height:44px\">\n" +
                    "                                            <p style=\"font-size:10px;line-height:1.4;margin:0;padding:0;color:rgb(102,102,102)\">Date & Time</p>\n" +
                    "                                            <p style=\"font-size:12px;line-height:1.4;margin:0;padding:0\">{date}, {time} EST</p>\n" +
                    "                                          </td>\n" +
                    "                                        </tr>\n" +
                    "                                      </tbody>\n" +
                    "                                    </table>\n" +
                    "                                    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
                    "                                      <tbody style=\"width:100%\">\n" +
                    "                                        <tr style=\"width:100%\">\n" +
                    "                                          <td data-id=\"__react-email-column\" style=\"padding-left:20px;border-style:solid;border-color:white;border-width:0px 1px 1px 0px;height:44px\">\n" +
                    "                                            <p style=\"font-size:10px;line-height:1.4;margin:0;padding:0;color:rgb(102,102,102)\">Service</p><p style=\"color:#15c;text-decoration:underline;font-size:12px;margin:0;padding:0;line-height:1.4\">{service}/{serviceFr}</p>\n" +
                    "                                          </td>\n" +
                    "                                        </tr>\n" +
                    "                                      </tbody>\n" +
                    "                                    </table>\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                              </tbody>\n" +
                    "                            </table>\n" +
                    "                          </td>\n" +
                    "                          <td data-id=\"__react-email-column\" style=\"padding-left:20px;border-style:solid;border-color:white;border-width:0px 1px 1px 0px;height:44px\">\n" +
                    "                            <p style=\"font-size:10px;line-height:1.4;margin:0;padding:0;color:rgb(102,102,102)\">Appointment Information</p>\n" +
                    "                            <p style=\"font-size:12px;line-height:1.4;margin:0;padding:0\">{location}</p>\n" +
                    "                            <p style=\"font-size:12px;line-height:1.4;margin:0;padding:0\">{firstName}</p>\n" +
                    "                            <p style=\"font-size:12px;line-height:1.4;margin:0;padding:0\">{lastName}</p>\n" +
                    "                            <p style=\"font-size:12px;line-height:1.4;margin:0;padding:0\">{phone}</p>\n" +
                    "                          </td>\n" +
                    "                        </tr>\n" +
                    "                      </tbody>\n" +
                    "                    </table>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>"+
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"padding:5px 20px 10px 20px\">\n" +
                    "              <tbody>\n" +
                    "                <tr>\n" +
                    "                  <td>\n" +
                    "                    <p style=\"font-size:14px;line-height:1.5;margin:16px 0\">For more information, Please contact<!-- --> <a href=\"#\" style=\"color:#067df7;text-decoration:underline\" target=\"_blank\">me</a></p>\n" +
                    "                    <p style=\"font-size:14px;line-height:1.5;margin:16px 0\">Thank you,<br />FitWSarah</p>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </tbody>\n" +
                    "            </table>\n" +
                    "          </td>\n" +
                    "        </tr>\n" +
                    "      </tbody>\n" +
                    "    </table>\n" +
                    "    <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\" style=\"max-width:580px;margin:0 auto\">\n" +
                    "      <tbody>\n" +
                    "        <tr>\n" +
                    "          <td>\n" +
                    "            <table align=\"center\" width=\"100%\" border=\"0\" cellPadding=\"0\" cellSpacing=\"0\" role=\"presentation\">\n" +
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
                    "          </td>\n" +
                    "        </tr>\n" +
                    "      </tbody>\n" +
                    "    </table>\n" +
                    "  </body>\n" +
                    "\n" +
                    "</html>";

            String emailContent = template.replace("{date}", appointment.getDate())
                    .replace("{time}", appointment.getTime())
                    .replace("{username}", account.getUsername())
                    .replace("{email}",account.getEmail())
                    .replace("{service}",fitnessPackage.getTitle_EN())
                    .replace("{serviceFr}",fitnessPackage.getTitle_FR())
                    .replace("{location}",appointment.getLocation())
                    .replace("{firstName}",appointment.getFirstName())
                    .replace("{lastName}",appointment.getLastName())
                    .replace("{phone}",appointment.getPhoneNum());

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("fitwithsarahfitness@gmail.com");
            helper.setTo(account.getEmail());
            helper.setSubject("Appointment Confirmation");
            helper.setText(emailContent, true);


            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
