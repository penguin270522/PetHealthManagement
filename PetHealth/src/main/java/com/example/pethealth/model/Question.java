package com.example.pethealth.model;

import com.example.pethealth.enums.QuestionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Question extends  BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "type_question_id")
    private TypeQuestion typeQuestion;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private QuestionStatus QuestionStatus;

    @Column(nullable = false, unique = true)
    private String code;

    @PrePersist
    public void generateRandomCode() {
        String letters = getRandomLetters(3);
        String numbers = getRandomNumbers(4);
        this.code = letters + numbers;
    }

    public static String getRandomLetters(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            result.append(alphabet.charAt(index));
        }
        return result.toString();
    }

    public static String getRandomNumbers(int length) {
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(digits.length());
            result.append(digits.charAt(index));
        }
        return result.toString();
    }

}
