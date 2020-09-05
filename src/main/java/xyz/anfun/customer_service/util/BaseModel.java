package xyz.anfun.customer_service.util;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseModel {
    // 主鍵
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    // 创建时间
    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    // 更新/修改时间
    @Column(name = "updated_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedAt;

    // 删除时间
    @Column(name = "deleted_at")
    protected Date deletedAt;

    // 状态
    @Column(name = "status")
    private Integer status = 1;
}
