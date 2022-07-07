package com.cnf.dao.infra;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.infra.LoginMuniAuthPeriod;

import com.cnf.domain.infra_heavy.LoginMuniAuthPeriodHeavy;
import com.cnf.domain.infra_heavy.OccChecklistSpaceTypeHeavy;
import java.util.List;

@Dao
public interface LoginMuniAuthPeriodDao {

    @Insert
    void insertLoginMuniAuthPeriodList(List<LoginMuniAuthPeriod> LoginMuniAuthPeriodList);

    @Query("SELECT * FROM LoginMuniAuthPeriod")
    List<LoginMuniAuthPeriod> selectLoginMuniAuthPeriodList();

    @Query("DELETE FROM LoginMuniAuthPeriod")
    void deleteAllLoginMuniAuthPeriodList();

    @Query("SELECT * FROM LoginMuniAuthPeriod JOIN Municipality m ON loginmuniauthperiod.muni_municode = m.municode")
    List<LoginMuniAuthPeriodHeavy> selectAllLoginMuniAuthPeriodHeavyList();

}
