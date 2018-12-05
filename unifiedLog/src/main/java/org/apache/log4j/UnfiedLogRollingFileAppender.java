package org.apache.log4j;

import com.liuxl.util.UnifiedLogLogger;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 包名如果不是org.apache.log4j 就无法super.获取父类参数
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class UnfiedLogRollingFileAppender extends DailyRollingFileAppender {

    private int maxFileSize = 7;

    @Override
    protected void subAppend(LoggingEvent event) {
        this.qw.write(this.layout.format(event));

        if (layout.ignoresThrowable()) {
            String[] s = event.getThrowableStrRep();
            if (s != null) {
                int len = s.length;
                for (int i = 0; i < len; i++) {
                    this.qw.write(s[i]);
                    this.qw.write(Layout.LINE_SEP);
                }
            }
        }

        if (this.immediateFlush) {
            this.qw.flush();
        }
    }

    public void setNow() {
        super.now = new Date();
    }

    @Override
    public void rollOver() throws IOException {
        super.rollOver();

        List<File> fileList = getAllLogs();
        sortFiles(fileList);
        deleteOvermuch(fileList);
    }

    private void deleteOvermuch(List<File> fileList) {
        if (fileList.size() > maxFileSize) {
            for (int i = 0; i < fileList.size() - maxFileSize; i++) {
                fileList.get(i).delete();
                UnifiedLogLogger.getSysLogger().warn("delete file" + fileList.get(i));
            }
        }
    }

    private void sortFiles(List<File> fileList) {
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                try {
                    if (getDateStr(o1).isEmpty()) {
                        return 1;
                    }
                    Date date = sdf.parse(getDateStr(o1));

                    if (getDateStr(o2).isEmpty()) {
                        return -1;
                    }
                    Date date1 = sdf.parse(getDateStr(o2));

                    if (date.getTime() > date1.getTime()) {
                        return 1;
                    } else if (date.getTime() < date1.getTime()) {
                        return -1;
                    }
                } catch (ParseException e) {
                    UnifiedLogLogger.getSysLogger().error(e.getMessage(), e);
                }
                return 0;
            }
        });
    }

    private String getDateStr(File file) {
        if (file == null) {
            return "null";
        }
        return file.getName().replaceAll(new File(fileName).getName(), "");
    }

    private List<File> getAllLogs() {
        final File file = new File(fileName);
        File logPath = file.getParentFile();
        if (logPath == null) {
            logPath = new File(".");
        }

        File files[] = logPath.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                try {
                    if (getDateStr(pathname).isEmpty()) {
                        return true;
                    }
                    sdf.parse(getDateStr(pathname));
                    return true;
                } catch (ParseException e) {
                    // logger.error("", e);
                    return false;
                }
            }
        });
        return Arrays.asList(files);
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
}
