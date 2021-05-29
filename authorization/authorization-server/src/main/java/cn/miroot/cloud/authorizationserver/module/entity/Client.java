package cn.miroot.cloud.authorizationserver.module.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 客户端
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "u_client")
@SQLDelete(sql = "update u_client set is_del = 0 where id = ?")
@Where(clause = "is_del = 1")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 租户ID
     */
    @Column(name = "tenant_id")
    private Long tenantId;

    /**
     * 客户端key
     */
    @Column(name = "client_key")
    private String clientKey;

    /**
     * 客户端密钥
     */
    @Column(name = "client_secret")
    private String clientSecret;

    /**
     * 删除标识
     */
    @Column(name = "is_del")
    private Boolean del = true;

}
