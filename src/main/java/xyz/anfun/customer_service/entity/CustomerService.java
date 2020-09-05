package xyz.anfun.customer_service.entity;

import lombok.Data;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author afungs
 * 客服表
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamicUpdate
@DynamicInsert
@SQLDelete(sql = "update cs_customer_services set deleted_at = now() where id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Table(name = "cs_customer_services")
public class CustomerService implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 20)
	private Long id;

	/**
	 * 用户id
	 */
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * 接待中的数量
	 */
	@Column(name = "visitors_num", nullable = true, length = 11)
	private Integer visitorsNum;

	/**
	 * 排队中的数量
	 */
	@Column(name = "queue_up_num", nullable = true, length = 11)
	private Integer queueUpNum;

	/**
	 * 自动接待客户数量
	 */
	@Column(name = "auto_receive_customer_num", nullable = true, length = 11)
	private Integer autoReceiveCustomerNum;

	@Column(name = "online")
	private Integer online;

	/**
	 * 状态
	 */
	@Column(name = "status", nullable = true, length = 11)
	private Integer status;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at", nullable = true)
	private java.util.Date createdAt;

	/**
	 * 删除时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "deleted_at", nullable = true)
	private java.util.Date deletedAt;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at", nullable = true)
	private java.util.Date updatedAt;
}
