/* 
 * This file is part of OppiaMobile - http://oppia-mobile.org/
 * 
 * OppiaMobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * OppiaMobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OppiaMobile. If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalcampus.oppia.application;

import java.io.File;

import org.digitalcampus.oppia.task.SubmitQuizTask;
import org.digitalcampus.oppia.task.SubmitTrackerMultipleTask;
import org.cbccessence.cch.tasks.StayingWellNotifyTask;
import org.cbccessence.cch.tasks.UpdateCCHLogTask;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

public class MobileLearning extends Application {

	public static final String TAG = MobileLearning.class.getSimpleName();

	public static final String FOLDER_NAME = "/Mlezi";

	// local storage vars
	public static final String OPPIAMOBILE_ROOT = Environment
			.getExternalStorageDirectory() + FOLDER_NAME + "/digitalcampus/";
	public static final String POC_ROOT = Environment
			.getExternalStorageDirectory() + FOLDER_NAME +  "/poc/";

	public static final String REFERENCES_ROOT = Environment
			.getExternalStorageDirectory() + FOLDER_NAME + "/lc/references/";

	public static final String COURSES_PATH = OPPIAMOBILE_ROOT + "modules/";
	public static final String MEDIA_PATH = OPPIAMOBILE_ROOT + "media/";
	public static final String DOWNLOAD_PATH = OPPIAMOBILE_ROOT + "download/";
	public static final String POC_DOWNLOAD_PATH = POC_ROOT + "download/";
	public static final String POC_SERVER_DOWNLOAD_PATH = "cch/yabr3/content/poccms/";
	public static final String POC_SERVER_CONTENT_DOWNLOAD_PATH = "cch/yabr3/content/poccms/downloadfile?fileName=";
	public static final String POC_SERVER_REFERENCE_DOWNLOAD_PATH = "cch/yabr3/content/poccms/downloadreference?fileName=";
	public static final String COURSE_XML = "module.xml";
	public static final String COURSE_SCHEDULE_XML = "schedule.xml";
	public static final String COURSE_TRACKER_XML = "tracker.xml";
	public static final String PRE_INSTALL_COURSES_DIR = "www/preload/courses"; // don't include leading or trailing slash
	public static final String PRE_INSTALL_MEDIA_DIR = "www/preload/media"; // don't include leading or trailing slash

	
	// server path vars - new version
	public static final String OPPIAMOBILE_API = "api/v1/";
	public static final String LOGIN_PATH = OPPIAMOBILE_API + "user/";
	public static final String REGISTER_PATH = OPPIAMOBILE_API + "register/";
	public static final String QUIZ_SUBMIT_PATH = OPPIAMOBILE_API + "quizattempt/";
	public static final String SERVER_COURSES_PATH = OPPIAMOBILE_API + "course/";
	public static final String SERVER_TAG_PATH = OPPIAMOBILE_API + "tag/";
	public static final String TRACKER_PATH = OPPIAMOBILE_API + "tracker/";
	public static final String SERVER_POINTS_PATH = OPPIAMOBILE_API + "points/";
	public static final String SERVER_COURSES_NAME = "courses";
	public static final String CCH_QUOTES_SUBMIT_PATH = "api/v1/quotes";
	public static final String CCH_USER_DETAILS_PATH = "cch/yabr3/api/v1/details/";
	//This is for usernames that contain only alphabets
	public static final String CCH_USER_DETAILS_WITH_INPUT_PATH = "cch/yabr3/api/v1/details?id=";
	public static final String FACILITY_TARGETS_PATH = "cch/yabr3/indicatorsdatabynurse/";
	public static final String FACILITY_TARGETS_SYNC_PATH = "cch/yabr3/getFacilityTargets?zone=";
	public static final String CCH_GROUPS_PATH = "cch/yabr3/getNurses?nurse_id=";
	public static final String CCH_USER_ACHIEVEMENTS_PATH = "cch/yabr3/api/v1/achievements/";
	public static final String CCH_COURSE_DETAILS_PATH = "cch/yabr3/courses";
	public static final String CCH_TRACKER_SUBMIT_PATH = "api/v1/tracker";
	public static final String CCH_COURSE_ACHIEVEMENT_PATH = "cmd=2&username=";
	public static final String CCH_REFERENCE_DOWNLOAD_PATH = "references/";
	public static final String CCH_DIAGNOSTIC = "Diagnostic Tool";
	public static final String CCH_REFERENCES = "References";
	public static final String CCH_COUNSELLING = "Counselling";

	// general other settings
	public static final String BUGSENSE_API_KEY = "f3a6ec3a";
	public static final int PASSWORD_MIN_LENGTH = 6;
	public static final int PAGE_READ_TIME = 3;
	public static final int RESOURCE_READ_TIME = 3;
	public static final String USER_AGENT = "OppiaMobile Android: ";
	public static final int QUIZ_PASS_THRESHOLD = 100;
	public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");
	public static final DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern("HH:mm:ss");
	public static final int MAX_TRACKER_SUBMIT = 10;
	public static final boolean DEVELOPER_MODE = false;
	
	// only used in case a course doesn't have any lang specified
	public static final String DEFAULT_LANG = "en";
	
	// for tracking if SubmitTrackerMultipleTask is already running
	//public SubmitTrackerMultipleTask omSubmitTrackerMultipleTask = null;
	
	// for tracking if SubmitQuizTask is already running
	public SubmitQuizTask omSubmitQuizTask = null;
	
	// for tracking if UpdateCCHLogTask is already running
	public UpdateCCHLogTask omUpdateCCHLogTask = null;
	
	// for tracking if notifier is already running
	public StayingWellNotifyTask omStayingWellNotifyTask = null;

	
	public static boolean createDirs() {
		String cardstatus = Environment.getExternalStorageState();
		if (cardstatus.equals(Environment.MEDIA_REMOVED)
				|| cardstatus.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			return false;
		}

		String[] dirs = { OPPIAMOBILE_ROOT, COURSES_PATH, MEDIA_PATH, DOWNLOAD_PATH };

		for (String dirName : dirs) {
			File dir = new File(dirName);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					return false;
				}
			} else {
				if (!dir.isDirectory()) {
					return false;
				}
			}
		}
		return true;
	}

    public static boolean doesFileExist(String file_name){

        File myFile = new File(REFERENCES_ROOT +  "/" + file_name + ".pdf");

        Log.i("Does file exist", "File you are looking for is " + myFile);


        boolean exist = myFile.exists();

        Log.i("Does file exist",  myFile.toString() + "? " + exist);
        return exist;

    }




    public static String doesTopicsFileExist(String file_locaton) {

        File folder = new File(file_locaton);

        Log.i("Does topics file exist", "File you are looking for is in " + folder);

        if (!folder.exists()) {
			Boolean status = folder.mkdirs();


				Log.i("Directory", "created? " + status);
                return null;




        } else { //folder already exist. search for file, if file, open else download

            File[] listOfFiles = folder.listFiles();

            for (File listOfFile : listOfFiles) {
                String filename = listOfFile.getName();
                String testName = filename;
                int pos = filename.lastIndexOf(".");
                Log.i("Files in Directory", folder + "\tName\t" + filename + "\tPosition\t" + pos);
                if (pos > 1) {
                    filename = filename.substring(0, pos);
                }
                if (testName.endsWith("1.xml") || testName.contains("_1")) { //file found, return filename
                    Log.i("Index File Found", "With name\t" + testName);
                    return filename;
                }
            }
        }
        return null;
    }




	 @Override
     public void onCreate() {
         super.onCreate();

         // The following line triggers the initialization of ACRA

     }
	public static boolean isLoggedIn(Activity act) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(act);
		return prefs.getBoolean("isSignedIn", false);
	}

}