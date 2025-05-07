import java.util.*;

public class OptimalPageReplacement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Get inputs for pages and number of frames
        System.out.println("Enter number of frames: ");
        int frames = sc.nextInt();
        
        System.out.println("Enter number of pages: ");
        int pagesCount = sc.nextInt();
        
        int[] pages = new int[pagesCount];
        System.out.println("Enter the page reference string: ");
        for (int i = 0; i < pagesCount; i++) {
            pages[i] = sc.nextInt();
        }
        
        // Initialize frames and other variables
        int[] frameArray = new int[frames];
        Arrays.fill(frameArray, -1);
        int pageFaults = 0;
        
        for (int i = 0; i < pagesCount; i++) {
            int currentPage = pages[i];
            
            if (!isInFrames(frameArray, currentPage)) {
                pageFaults++;
                int replaceIndex = findOptimalIndex(frameArray, pages, i + 1);
                frameArray[replaceIndex] = currentPage;
            }
            
            // Print the current state of frames
            System.out.print("Frames after inserting page " + currentPage + ": ");
            printFrames(frameArray);
        }
        
        System.out.println("Total page faults: " + pageFaults);
    }

    // Check if a page is already in frames
    private static boolean isInFrames(int[] frames, int page) {
        for (int frame : frames) {
            if (frame == page) {
                return true;
            }
        }
        return false;
    }

    // Find the optimal index for replacement
    private static int findOptimalIndex(int[] frames, int[] pages, int start) {
        int farthest = start;
        int index = -1;
        
        for (int i = 0; i < frames.length; i++) {
            int j;
            for (j = start; j < pages.length; j++) {
                if (frames[i] == pages[j]) {
                    if (j > farthest) {
                        farthest = j;
                        index = i;
                    }
                    break;
                }
            }
            if (j == pages.length) {
                return i; // Page not found in the future, replace this frame
            }
        }
        
        return (index == -1) ? 0 : index;
    }

    // Print the frames
    private static void printFrames(int[] frames) {
        for (int frame : frames) {
            if (frame == -1) {
                System.out.print("[ ] ");
            } else {
                System.out.print("[" + frame + "] ");
            }
        }
        System.out.println();
    }
}
