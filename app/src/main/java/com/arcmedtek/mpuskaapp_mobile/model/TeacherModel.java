package com.arcmedtek.mpuskaapp_mobile.model;

public class TeacherModel {
    String _courseName, _courseCode, _semester, _major, _firstName, _middleName, _lastName, _classRoom, _collegeYear;
    int _sks;

    public TeacherModel() {
    }

    public TeacherModel(String _courseName, String _courseCode, String _semester, String _major, String _firstName, String _middleName, String _lastName, String _classRoom, String _collegeYear, int _sks) {
        this._courseName = _courseName;
        this._courseCode = _courseCode;
        this._semester = _semester;
        this._major = _major;
        this._firstName = _firstName;
        this._middleName = _middleName;
        this._lastName = _lastName;
        this._classRoom = _classRoom;
        this._collegeYear = _collegeYear;
        this._sks = _sks;
    }

    public String get_courseName() {
        return _courseName;
    }

    public void set_courseName(String _courseName) {
        this._courseName = _courseName;
    }

    public String get_courseCode() {
        return _courseCode;
    }

    public void set_courseCode(String _courseCode) {
        this._courseCode = _courseCode;
    }

    public String get_semester() {
        return _semester;
    }

    public void set_semester(String _semester) {
        this._semester = _semester;
    }

    public String get_major() {
        return _major;
    }

    public void set_major(String _major) {
        this._major = _major;
    }

    public String get_firstName() {
        return _firstName;
    }

    public void set_firstName(String _firstName) {
        this._firstName = _firstName;
    }

    public String get_middleName() {
        return _middleName;
    }

    public void set_middleName(String _middleName) {
        this._middleName = _middleName;
    }

    public String get_lastName() {
        return _lastName;
    }

    public void set_lastName(String _lastName) {
        this._lastName = _lastName;
    }

    public String get_classRoom() {
        return _classRoom;
    }

    public void set_classRoom(String _classRoom) {
        this._classRoom = _classRoom;
    }

    public String get_collegeYear() {
        return _collegeYear;
    }

    public void set_collegeYear(String _collegeYear) {
        this._collegeYear = _collegeYear;
    }

    public int get_sks() {
        return _sks;
    }

    public void set_sks(int _sks) {
        this._sks = _sks;
    }
}
