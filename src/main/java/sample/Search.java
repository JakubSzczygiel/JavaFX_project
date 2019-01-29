package sample;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Search {
    public Collection<Task> searchTaskForDateRange(Collection<Task> tasks, Date dateFrom, Date dateTo) {
        List<Task> filteredTaskList = new ArrayList<>();
        for (Task task : tasks
        ) {
            Date date = task.getDate();
            if (IsDateInSpecificDateRange(date,dateFrom,dateTo)) {
                filteredTaskList.add(task);
            }
        }
        return filteredTaskList;
    }

    public Collection<Task> searchTaskAfterCertainDate (Collection<Task> tasks, Date dateFrom) {
        List<Task> filteredTaskList = new ArrayList<>();
        for (Task task : tasks
        ) {
            Date date = task.getDate();
            if (date.after(dateFrom) || date.equals(dateFrom)) {
                filteredTaskList.add(task);
            }
        }
        return filteredTaskList;
    }
    public Collection<Task> searchTaskBeforeCertainDate (Collection<Task> tasks, Date dateTo) {
        List<Task> filteredTaskList = new ArrayList<>();
        for (Task task : tasks
        ) {
            Date date = task.getDate();
            if (date.before(dateTo) || date.equals(dateTo)) {
                filteredTaskList.add(task);
            }
        }
        return filteredTaskList;
    }

    private boolean IsDateInSpecificDateRange(Date date, Date dateFrom, Date dateTo) {
        if ((date.after(dateFrom) && date.before(dateTo)) ||
                date.equals(dateFrom) ||
                date.equals(dateTo)) {
            return true;
        } else {
            return false;
        }
    }
    public Collection<Task> searchTaskForTaskDescription(Collection<Task> tasks, String stringWeLookFor)
    {
        List<Task> filteredTaskList = new ArrayList<>();
        for (Task task : tasks
        ) {
            String description = task.getDescription();
            if (description.contains(stringWeLookFor)) {
                filteredTaskList.add(task);
            }
        }
        return filteredTaskList;
    }

    public Collection<Task> searchTaskForTaskStatus(Collection<Task> tasks, String taskStatus)
    {
        List<Task> filteredTaskList = new ArrayList<>();
        for (Task task : tasks
        ) {
            if (taskStatus.equals(task.getStatus())) {
                filteredTaskList.add(task);
            }
        }
        return filteredTaskList;
    }
    public Collection<Task> searchTaskForTaskPriority(Collection<Task> tasks, String taskPriority)
    {
        List<Task> filteredTaskList = new ArrayList<>();
        for (Task task : tasks
        ) {
            if (taskPriority.equals(task.getPriority())) {
                filteredTaskList.add(task);
            }
        }
        return filteredTaskList;
    }

}

