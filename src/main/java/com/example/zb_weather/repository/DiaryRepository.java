package com.example.zb_weather.repository;

import com.example.zb_weather.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary , Integer> {
    List<Diary> findAllByDate(LocalDate date);

    List<Diary> findAllByDateBetween(LocalDate start, LocalDate end);

    Optional<Diary> findTopByDate(LocalDate date);

    int deleteAllByDate(LocalDate date);
}
