package com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer;

import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

@Embeddable
public interface AdminPanelRepository extends JpaRepository<AdminPanel, Integer> {
    AdminPanel findAdminPanelByAdminPanelIdentifier_AdminId(String adminId);
}
