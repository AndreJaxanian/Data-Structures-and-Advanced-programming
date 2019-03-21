#include <iostream>
#include <string>
#include <math.h>
#include <stdio.h>
using namespace std;


//................................a.City::Class...............................................
class city;
//..................................            ..............................................
class city {

public:
	string name;
	string type;
	string txtmsg;
	double SizeOftext, LenghtOfCall, LenghtOfVideoCall, WidthOfVideoCall, HeightOfVideoCall;
	bool text = false, voice = false, video = false;

	city() {

	}

	void Range() {
		if (type.find("TEXT") != std::string::npos) {
			text = true;
		}
		if (type.find("VOICE") != std::string::npos) {
			voice = true;
		}
		if (type.find("VIDEO") != std::string::npos) {
			video = true;
		}
	}

	
public:	//text message
	void TextMessage() {
		getline(cin, txtmsg);
	}
	virtual void DefineTextSize() {
	}
	void PriceOfText() {
		if (SizeOftext >= 0) {
			cout << "It costs: " << SizeOftext * 40 << "\n";
		}
		else {
			cout << "It costs: 0" << "\n";
		}
	}

	
public: // call
	void Call_Lenght() {
		cin >> LenghtOfCall;
	}
	void VideoVal() {
		cin >> LenghtOfCall;
		scanf("%d*%d", &WidthOfVideoCall, &HeightOfVideoCall);
	}
	virtual void DefineLenghtOfCall() {
	}
	void PriceOfVoiceCall(int** vect, int FirstInd, int SecondInd) {
		cout << "It Costs : " << (pow(LenghtOfCall, 2) * vect[FirstInd][SecondInd] / 10) << endl;
	}
	void PriceOfVideoCall(int ** vect, int FirstInd, int SecondInd) {
		cout << "It Costs : " << (LenghtOfCall * log((HeightOfVideoCall * WidthOfVideoCall)) * vect[FirstInd][SecondInd]) / 10 << endl;
	}

	virtual string sign() {
	}

};
int Numcity = 0;
//..........................Finding_Index_OF_The_Names .......................................
int FindName(city country[], string name) {
	for (int cont = 0; cont < Numcity; cont++) {
		if (country[cont].name == name) {
			return cont;
		}
	}
	return -1;
}
//............................................................................................
class UnSafe : public city {
public:
	string sign() {
		return "UnSafe";
	}
public:
	void DefineTextSize() {
		SizeOftext = txtmsg.length() - 3;
	}

	void DefineLenghtOfCall() {

	}


};
//............................................................................................
class Safe : public city {
public:
	string sign() {
		return "Safe";
	}
public:
	void DefineTextSize() {
		SizeOftext = txtmsg.length() - 3;
		SizeOftext = (log(pow(SizeOftext, 2)) * 200 + 30);
	}

	void DefineLenghtOfCall() {
		LenghtOfCall *= log(LenghtOfCall);
	}



};
const double P = 3.141592653;

//............................................................................................
class SemiSafe : public city {
public:
	string sign() {
		return "Semi-Safe";
	}
public:
	void DefineTextSize() {
		SizeOftext = txtmsg.length() - 3;
		SizeOftext = (log(SizeOftext) * 100 + 20);
	}

	void DefineLenghtOfCall() {
		LenghtOfCall *= pow(P, 2);
	}
};
//............................................................................................
class Fast : public city {

public:
	void DefineTextSize() {
		SizeOftext = txtmsg.length() - 3;
		SizeOftext *= ceil(SizeOftext / 140);
	}
};
//............................................................................................
city* Add(city first[], int* Numcity) {

	city* second = new city[(*Numcity + 1)];
	int cont;

	for (cont = 0; cont < *Numcity; cont++) {
		second[cont] = first[cont];
	}

	delete[] first;
	*Numcity += 1;

	return second;
}
//............................................................................................
int** Distance(city country[]) {

	int ** vect = new int*[Numcity];
	for (int cont = 0; cont < Numcity; cont++) {
		vect[cont] = new int[Numcity];
	}
	string FirstName, SecondName;

	for (int cont = 0; cont < Numcity; cont++) {
		for (int cont2 = 0; cont2 < Numcity; cont2++) {
			vect[cont][cont2] = -1;
		}
	}

	while (1) {
		cin >> FirstName;
		if (FirstName == "$") {
			break;
		}
		cin >> SecondName;
		cin >> vect[FindName(country, FirstName)][FindName(country, SecondName)];
		vect[FindName(country, SecondName)][FindName(country, FirstName)] = vect[FindName(country, FirstName)][FindName(country, SecondName)];
	}


	return vect;
}
//............................................................................................
city* Val() {

	city* City = new city[0];
	string name;

	while (1) {
		cin >> name;
		if (name == "#") {
			break;
		}
		City = Add(City, &Numcity);
		City[Numcity - 1].name = name;
		getline(cin, City[Numcity - 1].type);
		City[Numcity - 1].Range();
	}

	return City;
}
//............................................................................................
void TEXT(city* country, int** vect, city* point, int FirstInd, int SecondInd) {

	point->TextMessage();

	if (country[FirstInd].text && country[SecondInd].text) {
		if (vect[FirstInd][SecondInd] < 0) {
			cout << "No path found" << endl;
		}
		else {
			point->DefineTextSize();
			cout << "Distance: " << vect[FirstInd][SecondInd] << ", ";
			point->PriceOfText();
		}
	}
	else {
		cout << "TEXT is not availble in this path." << endl;
	}

}
//............................................................................................
void VIDEOCALL(city* country, int** vect, city* point, int FirstInd, int SecondInd) {

	point->VideoVal();

	if (country[FirstInd].video && country[SecondInd].video) {
		if (vect[FirstInd][SecondInd] < 0) {
			cout << "No path found" << endl;
		}
		else {
			point->DefineLenghtOfCall();
			cout << "Distance: " << vect[FirstInd][SecondInd] << ", ";
			point->PriceOfVideoCall(vect, FirstInd, SecondInd);
		}
	}
	else {
		cout << "VIDEO_CALL is not availble in this path." << endl;
	}
}
//............................................................................................
void VOICECALL(city* country, int** vect, city* point, int FirstInd, int SecondInd) {

	point->Call_Lenght();

	if (country[FirstInd].voice && country[SecondInd].voice) {
		if (vect[FirstInd][SecondInd] < 0) {
			cout << "No path found" << endl;
		}
		else {
			point->DefineLenghtOfCall();
			cout << "Distance: " << vect[FirstInd][SecondInd] << ", ";
			point->PriceOfVoiceCall(vect, FirstInd, SecondInd);
		}
	}
	else {
		cout << "VOICE_CALL is not availble in this path." << endl;
	}
}
//............................................................................................
void Start(city* country, int** vect) {

	string FIRST, SECOND, SecMode, txtmsg;
	//int voiceCallLength, LenghtOfVideoCall, WidthOfVideoCall, HeightOfVideoCall;
	int FirstInd, SecondInd;
	bool end = false;
	UnSafe unsafe;
	SemiSafe semiSafe;
	Safe safe;
	Fast fast;
	city* point = &unsafe;

	while (1) {

		cin >> FIRST;
		if (FIRST == "END") {
			break;
		}

		//Entering the SecMode:
		if (FIRST == "Safe") {
			point = &safe;
			continue;
		}
		if (FIRST == "Unsafe") {
			point = &unsafe;
			continue;
		}
		if (FIRST == "Semi-Safe") {
			point = &semiSafe;
			continue;
		}
		if (FIRST == "Fast") {
			if (point->sign() == "UnSafe") {
				point = &fast;
			}
			else {
				cout << "Not provided for this level of security." << endl;
			}
			continue;
		}
		cin >> SECOND;
		if (SECOND == "END") {
			break;
		}
		cin >> SecMode;
		if (SecMode == "END") {
			break;
		}

		//Finding index of cities
		FirstInd = FindName(country, FIRST);
		SecondInd = FindName(country, SECOND);

		if (FirstInd == -1 || SecondInd == -1) {
			cout << "Invalid city names." << endl;
			continue;
		}

		//Performing the order
		if (SecMode == "TEXT") {
			TEXT(country, vect, point, FirstInd, SecondInd);
		}
		else if (SecMode == "VOICE_CALL") {
			VOICECALL(country, vect, point, FirstInd, SecondInd);
		}
		else if (SecMode == "VIDEO_CALL") {
			VIDEOCALL(country, vect, point, FirstInd, SecondInd);
		}
		else {
			cout << "Invalid Command" << endl;
		}
	}
}
//............................................................................................
int main() {
	city* country = Val();
	int** vect = Distance(country);
	Start(country, vect);
	return 0;
}
