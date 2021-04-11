package com.nirmalya.ppmtool.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProjectTask {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(updatable = false, unique = true)
    private String projectSequence;
	
	@NotBlank(message = "Please include a project summary")
    private String summary;
	
	private String acceptanceCriteria;
	
	@Column(updatable = false)
    private String projectIdentifier;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    private BackLog backlog;
    
	private String status;
    
	private Integer priority;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date create_At;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date update_At;
    
	/*
	 * @ManyToOne(fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(name="backlog_id", updatable = false, nullable = false)
	 * 
	 * @JsonIgnore private BackLog backlog;
	 */
    
    @PrePersist
    protected void onCreate(){
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.update_At = new Date();
    }

	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectSequence=" + projectSequence + ", summary=" + summary
				+ ", acceptanceCriteria=" + acceptanceCriteria + ", projectIdentifier=" + projectIdentifier
				+ ", backlog=" + backlog + ", status=" + status + ", priority=" + priority + ", dueDate=" + dueDate
				+ ", create_At=" + create_At + ", update_At=" + update_At + "]";
	}

    
}
