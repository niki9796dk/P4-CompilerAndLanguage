package UnitTesting;

import AutoGen.MainParse;

import java.io.File;
import java.util.Objects;

public class ProductionRules {


    public static void main(String[] args) throws Exception {
        MainParse.isTest = true;

        File trueFolder = new File("tests/ProductionRules/ExpectTrue/");
        File falseFolder = new File("tests/ProductionRules/ExpectFalse/");

        System.out.println();
        testFilesInFolder(trueFolder, true);
        testFilesInFolder(falseFolder, false);

        // Possibly not required, but here anyways for edge cases.
        MainParse.isTest = false;
    }

    private static void testFilesInFolder(File folder, boolean expectedParse){
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile()) {
                parseFile(file, expectedParse);
            }
        }
    }

    private static boolean parseFile(File file, boolean expectedParse){
        try {
            MainParse.parseFile(file.getPath());
            System.out.print("Testing '" + file.getName() + "'");

        } catch (Exception e) {
            System.out.print("Testing '" + file.getName() + "'");

            printStatus(expectedParse, file);

            if (expectedParse) {
                e.printStackTrace();
            }

            return false;
        }

        printStatus(!expectedParse, file);
        return true;
    }

    private static void printStatus(boolean failed, File file){
        if (file.getName().length() > 13) {
            System.out.print("\t\t");
        } else {
            System.out.print("\t\t\t");
        }

        if (failed) {
            failure();
        } else {
            success();
        }
    }

    private static void success() {
        System.out.println(ANSI_GREEN + "SUCCESS." + ANSI_RESET);
    }

    private static void failure() {
        System.out.println(ANSI_RED + "FAILURE." + ANSI_RESET);
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
}
