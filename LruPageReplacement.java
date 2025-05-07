import java.util.*;

public class LruPageReplacement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input for number of frames
        System.out.print("Enter the number of frames: ");
        int numberOfFrames = scanner.nextInt();

        // Input for number of pages
        System.out.print("Enter the number of pages: ");
        int numberOfPages = scanner.nextInt();

        // Input for page reference string
        System.out.print("Enter the page reference string (space-separated): ");
        int[] pageReferenceString = new int[numberOfPages];
        for (int i = 0; i < numberOfPages; i++) {
            pageReferenceString[i] = scanner.nextInt();
        }

        // Use LinkedList to represent frames and track LRU
        LinkedList<Integer> frames = new LinkedList<>();
        int pageFaults = 0;

        // Iterate through the page reference string
        for (int page : pageReferenceString) {
            // If page is not in frames (page fault)
            if (!frames.contains(page)) {
                if (frames.size() >= numberOfFrames) {
                    frames.removeFirst();  // Remove the least recently used page
                }
                frames.addLast(page);  // Add new page at the end (most recently used)
                pageFaults++;  // Increment page fault count
            } else {
                // If page is already in frames, move it to the end (most recently used)
                frames.remove(frames.indexOf(page));
                frames.addLast(page);
            }

            // Display the current state of frames
            System.out.print("Frames: ");
            for (int frame : frames) {
                System.out.print(frame + " ");
            }
            System.out.println();
        }

        // Output the total page faults and page fault ratio
        System.out.println("Total Page Faults: " + pageFaults);
        System.out.println("Page Faults ratio: " + pageFaults + ":" + numberOfPages);

        scanner.close();
    }
}
