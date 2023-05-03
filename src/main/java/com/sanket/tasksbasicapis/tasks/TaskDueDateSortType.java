package com.sanket.tasksbasicapis.tasks;

public enum TaskDueDateSortType {
    UNDEFINED("undefined"),
    DATE_ASC("dateAsc"),
    DATE_DESC("dateDesc");

    private final String dateSortType;

    TaskDueDateSortType(String dateSortType) {
        this.dateSortType = dateSortType;
    }

    public static TaskDueDateSortType fromString(String text) {
        for (TaskDueDateSortType b : TaskDueDateSortType.values()) {
            if (b.dateSortType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return UNDEFINED;
    }

    public String getDateSortType() {
        return dateSortType;
    }
}
