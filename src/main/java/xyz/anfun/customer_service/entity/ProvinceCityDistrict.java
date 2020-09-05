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
 * null
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamicUpdate
@DynamicInsert
@SQLDelete(sql = "update cs_province_city_districts set deleted_at = now() where id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Table(name = "cs_province_city_districts")
public class ProvinceCityDistrict implements Serializable {

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
	 * 编号
	 */
	@Column(name = "code", nullable = true, length = 40)
	private String code;

	/**
	 * 名称
	 */
	@Column(name = "name", nullable = true, length = 100)
	private String name;

	/**
	 * 父级编号
	 */
	@Column(name = "pid", nullable = true, length = 20)
	private Long pid;
}
