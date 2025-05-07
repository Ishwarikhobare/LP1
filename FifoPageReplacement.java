import java.util.*;

public class FifoPageReplacement {
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

        // Initialize frames array and variables
        int[] frames = new int[numberOfFrames];
        Arrays.fill(frames, -1);  // Fill frames with -1 (indicating empty)
        int pageFaults = 0;
        int currentIndex = 0;

        // Iterate through the page reference string
        for (int page : pageReferenceString) {
            boolean pageHit = false;

            // Check if the page is already in one of the frames (page hit)
            for (int frame : frames) {
                if (frame == page) {
                    pageHit = true;
                    break;
                }
            }

            // If it's a page fault (page is not in any frame)
            if (!pageHit) {
                frames[currentIndex] = page;  // Replace the page in the FIFO order
                currentIndex = (currentIndex + 1) % numberOfFrames;  // Move to next frame index
                pageFaults++;
            }

            // Display the current state of frames after each page reference
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
