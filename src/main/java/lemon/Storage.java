package lemon;
/**
 * Handle loading and saving of the list of tasks from a txt file to the TaskList
 * @author He Yiheng
 */

import lemon.exception.DescriptionException;
import lemon.task.Deadline;
import lemon.task.Event;
import lemon.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Storage {

    private final String FILE_PATH;

    public Storage() {
        this.FILE_PATH = "data/lemonSaves.txt";
    }

    public Storage(String filePath){
        this.FILE_PATH = filePath;
    }

    /**
     * Loads the tasks from a txt file into the TaskList that is passed into
     * Creates the directory and file if it does not exist
     * @param tasks TaskList that will have the tasks loaded into from the file
     * @return true if all tasks within the file is loaded successfully
     */
    public boolean loadTasks(TaskList tasks) {
        try {
            File f = new File(FILE_PATH);
            f.getParentFile().mkdirs();
            f.createNewFile();
            Scanner fileScanner = new Scanner(f);

            while (fileScanner.hasNextLine()) {
                String[] temp = fileScanner.nextLine().split("\\|");
                switch (temp[0]) {
                    case "T":
                        tasks.addNewTask(new Todo(temp[2], Boolean.parseBoolean(temp[1])));
                        break;
                    case "D":
                        tasks.addNewTask(new Deadline(temp[2],
                                LocalDate.parse(temp[3], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                Boolean.parseBoolean(temp[1])));
                        break;
                    case "E":
                        tasks.addNewTask(new Event(temp[2],
                                LocalDate.parse(temp[3], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                LocalDate.parse(temp[4], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                Boolean.parseBoolean(temp[1])));
                        break;
                }
            }

            return true;
        } catch (IOException e) {
            System.out.print(" Im sowwy... Something went wrong, QwQ. Unable to create file.\n" +
                    " I dont think i can do this anymore");
            return false;
        } catch (DescriptionException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Saves all the tasks in the provided TaskList into the txt file
     * @param tasks TaskList the files is being saved from
     * @return true if all tasks within the TaskList is saved successfully 
     */
    public boolean saveTasks(TaskList tasks) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);

            for (int i = 0; i < tasks.size(); i++) {
                fw.write(tasks.get(i).toFileString());
            }

            fw.close();
            return true;
        } catch (IOException e) {
            System.out.println("Unable to save into file.\n" +
                    " Please make sure that \"lemonSaves.txt\" exists properly in\n" +
                    FILE_PATH);
            return false;
        }
    }
}
