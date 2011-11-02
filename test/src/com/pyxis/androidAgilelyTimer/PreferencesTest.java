package com.pyxis.androidAgilelyTimer;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.mock.MockContext;
import junit.framework.TestCase;

import java.util.Map;

public class PreferencesTest extends TestCase {

    private AgilelyTimerPreferencesAccessor prefs;
    private MockContext mockContext;
    private SharedPreferencesMock mockSharedPreferences;
    private MyEditorMock editorMock;


    public void testLoadingUnsavedStandupValuesShouldBeDefaultValues(){

        int length = prefs.getStandupLenghtOfMeeting();
        int numberOfMembers = prefs.getStandupNumberOfMembers();

        assertEquals(2, mockSharedPreferences.getIntNumberOfInvocation);

        assertEquals(length, AgilelyTimerPreferencesAccessor.DEFAULT_VALUE);
        assertEquals(numberOfMembers, AgilelyTimerPreferencesAccessor.DEFAULT_VALUE);
    }

    public void testLoadingUnsavedTimeboxValuesShouldBeDefaultValues(){
        int length = prefs.getTimeboxMeetingLength();

        assertEquals(1, mockSharedPreferences.getIntNumberOfInvocation);

        assertEquals(length, AgilelyTimerPreferencesAccessor.DEFAULT_VALUE);
    }

    public void testSavedStandupPreferencesCanBeReloaded() {
        int fiveminutes = 5;
        int sevenmembers = 7;
        prefs.setStandupPreferences(fiveminutes, sevenmembers);

        int length = prefs.getStandupLenghtOfMeeting();
        int numberOfMembers = prefs.getStandupNumberOfMembers();


        assertEquals(2, editorMock.putIntNumberOfInvocation);
        assertEquals(2, mockSharedPreferences.getIntNumberOfInvocation);
    }


    public void testSavedTimeboxPreferencesCanBeReloaded() {
        int fiveminutes = 5;
        prefs.setTimeboxPreferences(fiveminutes);

        int length = prefs.getTimeboxMeetingLength();

        assertEquals(1, editorMock.putIntNumberOfInvocation);
        assertEquals(1, mockSharedPreferences.getIntNumberOfInvocation);
    }

    public void setUp() {
        editorMock = new MyEditorMock();
        mockSharedPreferences = new SharedPreferencesMock();

        mockSharedPreferences.getEditorReturnValue = editorMock;
        mockContext = new MyMockContext(mockSharedPreferences);

        prefs = new AgilelyTimerPreferencesAccessor(mockContext);
    }

    public void tearDown() {
    }


      //// HELPER Classes
    private class MyMockContext extends MockContext{

        private SharedPreferencesMock returnedOnGetSharedPreferences;

        public MyMockContext(SharedPreferencesMock returnedOnGetSharedPreferences){
                  this.returnedOnGetSharedPreferences = returnedOnGetSharedPreferences;
        }

        public android.content.SharedPreferences getSharedPreferences(java.lang.String name, int mode) {
            return  returnedOnGetSharedPreferences;

        }
    }



    private class SharedPreferencesMock implements SharedPreferences{

        public Editor getEditorReturnValue;
        public int setIntNumberOfInvocation;
        public int getIntNumberOfInvocation;
        public final static int GET_INT_RETURN_VALUE = -1;


        public Map<String, ?> getAll() {
            return null;
        }

        public String getString(String s, String s1) {
            return null;
        }

        public int getInt(String s, int i) {
            getIntNumberOfInvocation++;
            return GET_INT_RETURN_VALUE;
        }

        public long getLong(String s, long l) {
            return 0;
        }

        public float getFloat(String s, float v) {
            return 0;
        }

        public boolean getBoolean(String s, boolean b) {
            return false;
        }

        public boolean contains(String s) {
            return false;
        }

        public Editor edit() {
            return getEditorReturnValue;
        }

        public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

        }

        public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

        }
    }

    public class MyEditorMock implements SharedPreferences.Editor{

        public int putIntNumberOfInvocation;

        public SharedPreferences.Editor putString(String s, String s1) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public SharedPreferences.Editor putInt(String s, int i) {
            putIntNumberOfInvocation++;
            return null;
        }

        public SharedPreferences.Editor putLong(String s, long l) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public SharedPreferences.Editor putFloat(String s, float v) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public SharedPreferences.Editor putBoolean(String s, boolean b) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public SharedPreferences.Editor remove(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public SharedPreferences.Editor clear() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public boolean commit() {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}
