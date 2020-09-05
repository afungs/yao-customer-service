package xyz.anfun.customer_service.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
 * null
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamicUpdate
@DynamicInsert
@SQLDelete(sql = "update cs_users set deleted_at = now() where id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Table(name = "cs_users")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 20)
	private Long id;

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
	 * 状态
	 */
	@Column(name = "status", nullable = true, length = 11)
	private Integer status;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at", nullable = true)
	private java.util.Date updatedAt;

	/**
	 * 年龄
	 */
	@Column(name = "age", nullable = true, length = 11)
	private Integer age;

	/**
	 * 头像
	 */
	@Column(name = "avatar", nullable = true, length = 120)
	private String avatar;

	/**
	 * 生日
	 */
	@Column(name = "birthday", nullable = true, length = 20)
	private String birthday;

	/**
	 * 昵称
	 */
	@Column(name = "nick_name", nullable = true, length = 16)
	private String nickName;

	/**
	 * 密码
	 */
	@Column(name = "password", nullable = true, length = 32)
	private String password;

	/**
	 * 手机号
	 */
	@Column(name = "phone", nullable = true, length = 11)
	private String phone;

	/**
	 * 性别
	 */
	@Column(name = "sex", nullable = true, length = 11)
	private Integer sex;

	/**
	 * 用户名
	 */
	@Column(name = "user_name", nullable = true, length = 16)
	private String userName;

	/**
	 * 微信openid
	 */
	@Column(name = "we_chat_openid", nullable = true, length = 255)
	private String weChatOpenId;

	/**
	 * 城市
	 */
	@OneToOne
	@JoinColumn(name = "city_id")
	private ProvinceCityDistrict city;

	/**
	 * 区县
	 */
	@OneToOne
	@JoinColumn(name = "district_id")
	private ProvinceCityDistrict district;

	/**
	 * 省份
	 */
	@OneToOne
	@JoinColumn(name = "province_id")
	private ProvinceCityDistrict province;

	/**
	 * 用户角色
	 */
	@OneToOne
	@JoinColumn(name = "role_id")
	private Role role;
}
