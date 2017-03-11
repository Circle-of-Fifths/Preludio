package engine;

import java.io.File;

/**
 * Created by Fox Kiester on 3/09/17.
 */
public final class CurrentLesson {
    private static File currentLessonMidi;
    private static File currentLessonText;
    private static boolean playLesson;

    private CurrentLesson() {

    }

    public static void setMidi(File midi) {
        currentLessonMidi = midi;
    }

    public static File getMidi() {
        return currentLessonMidi;
    }

    public static void setText(File text) {
        currentLessonText = text;
    }

    public static File getText() {
        return currentLessonText;
    }

    public static void setPlayLesson(boolean val) {
        playLesson = val;
    }

    public static boolean playLesson() {
        return playLesson && (currentLessonMidi != null);
    }

}
