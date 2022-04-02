package com.example.flashcard_app;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {com.example.flashcard_app.Flashcard.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract com.example.flashcard_app.FlashcardDao flashcardDao();
}
