package com.example.zb_weather.service;

import com.example.zb_weather.entity.Diary;
import com.example.zb_weather.repository.DiaryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DiaryServiceTest {
    @Autowired
    DiaryService diaryService;

    @Autowired
    DiaryRepository diaryRepository;

    @Test
    void postDiary() {
        LocalDate today = LocalDate.now();
        String text = "테스트 일기입니다.";

        Diary diary = diaryService.postDiary(today, text);

        assertEquals(diary.getText(), "테스트 일기입니다.");
    }

    @Test
    void getDiaryListByDate() {
        LocalDate today = LocalDate.now();
        String text = "테스트 일기입니다.";

        Diary diary = diaryService.postDiary(today, text);
        Diary diary2 = diaryService.postDiary(today, text);
        Diary diary3 = diaryService.postDiary(today, text);

        assertEquals(diaryRepository.findAllByDate(today).size(), diaryService.getDiaryListByDate(today).size());
    }

    @Test
    void getDiaryListBetweenStartDateEndDate() {
        LocalDate dayBeforeYesterday = LocalDate.now().minusDays(2);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate today = LocalDate.now();

        String text = "테스트 일기입니다.";
        Diary diary = diaryService.postDiary(today, text);
        Diary diary2 = diaryService.postDiary(dayBeforeYesterday, text);
        Diary diary3 = diaryService.postDiary(yesterday, text);

        List<Diary> diaryListBetweenStartDateEndDate = diaryService.getDiaryListBetweenStartDateEndDate(yesterday, today);

        assertEquals(2,diaryListBetweenStartDateEndDate.size());
    }

    @Test
    void updateDiary() {
        LocalDate today = LocalDate.now();

        String text = "테스트 일기입니다.";
        Diary diary = diaryService.postDiary(today, text);

        Diary updateDairy = diaryService.updateDiary(today, "수정된 일기입니다.");

        assertEquals(updateDairy.getText(), "수정된 일기입니다.");
        assertEquals(updateDairy.getId(), diary.getId());
    }

    @Test
    void deleteDiary() {
        LocalDate today = LocalDate.now();

        String text = "테스트 일기입니다.";
        Diary diary = diaryService.postDiary(today, text);
        Diary diary2 = diaryService.postDiary(today, text);
        Diary diary3 = diaryService.postDiary(today, text);

        int deleteDiaryCount = diaryService.deleteDiary(today);

        assertEquals(deleteDiaryCount, 3);
        assertEquals(diaryRepository.findAll().size(),0);
    }
}