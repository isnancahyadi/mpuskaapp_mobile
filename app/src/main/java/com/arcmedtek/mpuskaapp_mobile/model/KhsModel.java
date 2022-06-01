package com.arcmedtek.mpuskaapp_mobile.model;

public class KhsModel {
    String _nim, _studentFirstName, _studentMiddleName, _studentLastName, _courseCode, _courseName, _lectureFrontDegree, _lectureFirstName, _lectureMiddleName, _lectureLastName, _lectureBackDegree, _semester, _department, _classRoom, _collegeYear, _assessment, _foto, _teamName, _cpl, _cpmk;
    int _idKhs, _idKrs, _idAsesmen, _sks, _score, _percent;

    public KhsModel() {
    }

    public KhsModel(int _idKhs, int _idKrs, int _idAsesmen, String _nim, String _studentFirstName, String _studentMiddleName, String _studentLastName, String _courseCode, String _courseName, String _lectureFrontDegree, String _lectureFirstName, String _lectureMiddleName, String _lectureLastName, String _lectureBackDegree, String _semester, String _department, String _classRoom, String _collegeYear, String _assessment, String _foto, String _teamName, String _cpl, String _cpmk, int _sks, int _score, int _percent) {
        this._idKhs = _idKhs;
        this._idKrs = _idKrs;
        this._idAsesmen = _idAsesmen;
        this._nim = _nim;
        this._studentFirstName = _studentFirstName;
        this._studentMiddleName = _studentMiddleName;
        this._studentLastName = _studentLastName;
        this._courseCode = _courseCode;
        this._courseName = _courseName;
        this._lectureFrontDegree = _lectureFrontDegree;
        this._lectureFirstName = _lectureFirstName;
        this._lectureMiddleName = _lectureMiddleName;
        this._lectureLastName = _lectureLastName;
        this._lectureBackDegree = _lectureBackDegree;
        this._semester = _semester;
        this._department = _department;
        this._classRoom = _classRoom;
        this._collegeYear = _collegeYear;
        this._assessment = _assessment;
        this._foto = _foto;
        this._teamName = _teamName;
        this._cpl = _cpl;
        this._cpmk = _cpmk;
        this._sks = _sks;
        this._score = _score;
        this._percent = _percent;
    }

    public int get_idKhs() {
        return _idKhs;
    }

    public void set_idKhs(int _idKhs) {
        this._idKhs = _idKhs;
    }

    public int get_idKrs() {
        return _idKrs;
    }

    public void set_idKrs(int _idKrs) {
        this._idKrs = _idKrs;
    }

    public int get_idAsesmen() {
        return _idAsesmen;
    }

    public void set_idAsesmen(int _idAsesmen) {
        this._idAsesmen = _idAsesmen;
    }

    public String get_nim() {
        return _nim;
    }

    public void set_nim(String _nim) {
        this._nim = _nim;
    }

    public String get_studentFirstName() {
        return _studentFirstName;
    }

    public void set_studentFirstName(String _studentFirstName) {
        this._studentFirstName = _studentFirstName;
    }

    public String get_studentMiddleName() {
        return _studentMiddleName;
    }

    public void set_studentMiddleName(String _studentMiddleName) {
        this._studentMiddleName = _studentMiddleName;
    }

    public String get_studentLastName() {
        return _studentLastName;
    }

    public void set_studentLastName(String _studentLastName) {
        this._studentLastName = _studentLastName;
    }

    public String get_courseCode() {
        return _courseCode;
    }

    public void set_courseCode(String _courseCode) {
        this._courseCode = _courseCode;
    }

    public String get_courseName() {
        return _courseName;
    }

    public void set_courseName(String _courseName) {
        this._courseName = _courseName;
    }

    public String get_lectureFrontDegree() {
        return _lectureFrontDegree;
    }

    public void set_lectureFrontDegree(String _lectureFrontDegree) {
        this._lectureFrontDegree = _lectureFrontDegree;
    }

    public String get_lectureFirstName() {
        return _lectureFirstName;
    }

    public void set_lectureFirstName(String _lectureFirstName) {
        this._lectureFirstName = _lectureFirstName;
    }

    public String get_lectureMiddleName() {
        return _lectureMiddleName;
    }

    public void set_lectureMiddleName(String _lectureMiddleName) {
        this._lectureMiddleName = _lectureMiddleName;
    }

    public String get_lectureLastName() {
        return _lectureLastName;
    }

    public void set_lectureLastName(String _lectureLastName) {
        this._lectureLastName = _lectureLastName;
    }

    public String get_lectureBackDegree() {
        return _lectureBackDegree;
    }

    public void set_lectureBackDegree(String _lectureBackDegree) {
        this._lectureBackDegree = _lectureBackDegree;
    }

    public String get_semester() {
        return _semester;
    }

    public void set_semester(String _semester) {
        this._semester = _semester;
    }

    public String get_department() {
        return _department;
    }

    public void set_department(String _department) {
        this._department = _department;
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

    public String get_assessment() {
        return _assessment;
    }

    public void set_assessment(String _assessment) {
        this._assessment = _assessment;
    }

    public String get_foto() {
        return _foto;
    }

    public void set_foto(String _foto) {
        this._foto = _foto;
    }

    public String get_teamName() {
        return _teamName;
    }

    public void set_teamName(String _teamName) {
        this._teamName = _teamName;
    }

    public String get_cpl() {
        return _cpl;
    }

    public void set_cpl(String _cpl) {
        this._cpl = _cpl;
    }

    public String get_cpmk() {
        return _cpmk;
    }

    public void set_cpmk(String _cpmk) {
        this._cpmk = _cpmk;
    }

    public int get_sks() {
        return _sks;
    }

    public void set_sks(int _sks) {
        this._sks = _sks;
    }

    public int get_score() {
        return _score;
    }

    public void set_score(int _score) {
        this._score = _score;
    }

    public int get_percent() {
        return _percent;
    }

    public void set_percent(int _percent) {
        this._percent = _percent;
    }
}
