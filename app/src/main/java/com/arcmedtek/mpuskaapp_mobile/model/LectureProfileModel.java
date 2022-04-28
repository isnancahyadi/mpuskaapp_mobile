package com.arcmedtek.mpuskaapp_mobile.model;

public class LectureProfileModel {
    String _niy, _frontDegree, _firstName, _middleName, _lastName, _backDegree, _gender, _phoneNumber, _email, _photo;

    public LectureProfileModel() {
    }

    public LectureProfileModel(String _niy, String _frontDegree, String _firstName, String _middleName, String _lastName, String _backDegree, String _gender, String _phoneNumber, String _email, String _photo) {
        this._niy = _niy;
        this._frontDegree = _frontDegree;
        this._firstName = _firstName;
        this._middleName = _middleName;
        this._lastName = _lastName;
        this._backDegree = _backDegree;
        this._gender = _gender;
        this._phoneNumber = _phoneNumber;
        this._email = _email;
        this._photo = _photo;
    }

    public String get_niy() {
        return _niy;
    }

    public void set_niy(String _niy) {
        this._niy = _niy;
    }

    public String get_frontDegree() {
        return _frontDegree;
    }

    public void set_frontDegree(String _frontDegree) {
        this._frontDegree = _frontDegree;
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

    public String get_backDegree() {
        return _backDegree;
    }

    public void set_backDegree(String _backDegree) {
        this._backDegree = _backDegree;
    }

    public String get_gender() {
        return _gender;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public String get_phoneNumber() {
        return _phoneNumber;
    }

    public void set_phoneNumber(String _phoneNumber) {
        this._phoneNumber = _phoneNumber;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_photo() {
        return _photo;
    }

    public void set_photo(String _photo) {
        this._photo = _photo;
    }
}
