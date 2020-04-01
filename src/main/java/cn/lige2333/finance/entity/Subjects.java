package cn.lige2333.finance.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class Subjects implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String username;
    private String title;
    private String content;
    private Date date;
}
