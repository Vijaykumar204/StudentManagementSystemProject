package com.studentmanagementsystem.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;

@Repository
public interface DailyAttendanceRepository extends JpaRepository<DailyAttendanceModel,Long> {

}
