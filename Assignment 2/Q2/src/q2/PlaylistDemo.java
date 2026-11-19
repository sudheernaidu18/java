package q2;

import java.util.*;

/**
 * Question 2: Music Playlist Editor Demo
 * Demonstrates playlist operations across ArrayList, LinkedList, and ArrayDeque.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class PlaylistDemo {

    public static class Song {
        public final String id;
        public final String title;
        public final String artist;
        public final int durationSeconds;

        public Song(String id, String title, String artist, int durationSeconds) {
            this.id = id;
            this.title = title;
            this.artist = artist;
            this.durationSeconds = durationSeconds;
        }

        @Override
        public String toString() {
            return String.format("%s - %s (%d:%02d)", artist, title, durationSeconds / 60, durationSeconds % 60);
        }
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("Q2: MUSIC PLAYLIST EDITOR - ARRAYLIST VS LINKEDLIST VS DEQUE");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        // 1. ArrayList Playlist
        List<Song> arrayListPlaylist = new ArrayList<>();
        arrayListPlaylist.add(new Song("S01", "Midnight Vibes", "Luna", 210));
        arrayListPlaylist.add(new Song("S02", "Solar Flare", "Aero", 185));
        arrayListPlaylist.add(new Song("S03", "Ocean Drive", "Wave", 240));

        System.out.println("--- 1. Initial ArrayList Playlist ---");
        for (int i = 0; i < arrayListPlaylist.size(); i++) {
            System.out.printf("  [%d] %s\n", i, arrayListPlaylist.get(i));
        }

        // Inserting at index 1
        arrayListPlaylist.add(1, new Song("S04", "Echoes of Time", "Chrono", 195));
        System.out.println("\nAfter inserting 'Echoes of Time' at position 1:");
        System.out.println("  k-th element [1]: " + arrayListPlaylist.get(1));

        // 2. Changed Condition: "Up Next" Queue using ArrayDeque
        System.out.println("\n--- 2. Changed Condition: 'Up Next' Queue (ArrayDeque) ---");
        ArrayDeque<Song> upNextQueue = new ArrayDeque<>();
        // Frequently added at both ends:
        upNextQueue.addLast(new Song("Q01", "Next Track 1", "DJ Synth", 200));
        upNextQueue.addFirst(new Song("Q00", "Priority VIP Track", "Star", 180));
        upNextQueue.addLast(new Song("Q02", "Next Track 2", "Bass", 220));

        System.out.println("Up Next Queue Contents:");
        for (Song s : upNextQueue) {
            System.out.println("  -> " + s);
        }

        // Frequently removed from front:
        Song played = upNextQueue.pollFirst();
        System.out.println("\nPlaying (pollFirst): " + played);
        System.out.println("Remaining Queue Size: " + upNextQueue.size());
    }
}
