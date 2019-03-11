package UnitTesting;

import AutoGen.MainParse;

import java.io.File;
import java.util.Objects;

public class ProductionRules {
    public static void main(String[] args) throws Exception {
        File trueFolder = new File("tests/ProductionRules/ExpectTrue/");
        File falseFolder = new File("tests/ProductionRules/ExpectFalse/");

        testFilesInFolder(trueFolder, true);
        testFilesInFolder(falseFolder, false);


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
            System.out.print("Testing '" + file.getName() + "' ");

        } catch (Exception e) {
            System.out.print("Testing '" + file.getName() + "' ");

            printStatus(expectedParse, file);

            if (expectedParse) {
                e.printStackTrace();
            }

            return false;
        }

        printStatus(!expectedParse, file);
        return true;
    }

    private static void printStatus(boolean failure, File file){
        if (file.getName().length() > 15) {
            System.out.print("\t");
        } else {
            System.out.print("\t\t");
        }

        if (failure) {
            failure();
        } else {
            success();
        }
    }

    private static void success() {
        System.out.println("SUCCESS.\n");
    }

    private static void failure() {
        System.out.println("FAILURE.\n");
    }
}
