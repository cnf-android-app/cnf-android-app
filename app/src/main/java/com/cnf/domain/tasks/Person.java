package com.cnf.domain.tasks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Person {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "personid")
  private Integer personId;
  @ColumnInfo(name = "fname")
  private String firstName;
  @ColumnInfo(name = "lname")
  private String lastName;
  @ColumnInfo(name = "jobtitle")
  private String jobTitle;
  @ColumnInfo(name = "phonecell")
  private String phoneCell;
  @ColumnInfo(name = "phonehome")
  private String phoneHome;
  @ColumnInfo(name = "phonework")
  private String phoneWork;
  @ColumnInfo(name = "email")
  private String email;

  public Integer getPersonId() {
    return personId;
  }

  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getPhoneCell() {
    return phoneCell;
  }

  public void setPhoneCell(String phoneCell) {
    this.phoneCell = phoneCell;
  }

  public String getPhoneHome() {
    return phoneHome;
  }

  public void setPhoneHome(String phoneHome) {
    this.phoneHome = phoneHome;
  }

  public String getPhoneWork() {
    return phoneWork;
  }

  public void setPhoneWork(String phoneWork) {
    this.phoneWork = phoneWork;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Person{" +
        "personId=" + personId +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", jobTitle='" + jobTitle + '\'' +
        ", phoneCell='" + phoneCell + '\'' +
        ", phoneHome='" + phoneHome + '\'' +
        ", phoneWork='" + phoneWork + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
