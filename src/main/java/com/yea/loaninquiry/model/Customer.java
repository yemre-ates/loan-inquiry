package com.yea.loaninquiry.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TBL_LOAN_CUSTOMERS")
public class Customer {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ_TBL_LOAN_CUSTOMERS")
	@Getter
    private Long id;
	
	@Column(name="T_TCKN")
	@Getter
	@Setter
	private String tckn;	
	
	@Column(name="T_NAME")
	@Getter
	@Setter
	private String name;
	
	@Column(name="T_INCOME")
	@Getter
	@Setter
	private int income;
	
	@Column(name="T_MSISDN")
	@Getter
	@Setter
	private String msisdn;
	
	@Column(name="T_LOANSTATUS")
	@Getter
	@Setter
	private LoanStatus loanstatus;
	
	@Column(name="T_LIMIT")
	@Getter
	@Setter
	private int limit;

	@Column(name="D_INQUIRY_DATE")
	@Getter
	private Date entDate = new Date();

}
