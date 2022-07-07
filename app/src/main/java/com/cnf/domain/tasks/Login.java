package com.cnf.domain.tasks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Login {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "userid")
  private Integer userId;
  @ColumnInfo(name = "personlink")
  private Integer personId;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getPersonId() {
    return personId;
  }

  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

  @Override
  public String toString() {
    return "Login{" +
        "userId=" + userId +
        ", personId=" + personId +
        '}';
  }
}
