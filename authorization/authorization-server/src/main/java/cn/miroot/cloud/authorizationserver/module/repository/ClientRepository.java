package cn.miroot.cloud.authorizationserver.module.repository;

import cn.miroot.cloud.authorizationserver.module.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author fupan
 */
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

}