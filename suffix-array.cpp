#include <cstring>
#include <iostream>
#include <algorithm>
#include <cmath>
using namespace std;
#define MAXN 1000000
#define MAXLG 25
#define RANGE 255

struct entry {
    int nr[2], p;
} L[MAXN];

char A[2][MAXN];
int P[2][MAXLG][MAXN];
int N, i, stp, cnt;
int counts[250];
int cumulativeCounts[250];
int cmp(struct entry a, struct entry b) {

    return a.nr[0] == b.nr[0] ? (a.nr[1] < b.nr[1] ? 1 : 0) : (a.nr[0] < b.nr[0] ? 1 : 0);
}
setCountsArray(){
    
    for(int i = 0; i < 250; i++)
    {
         counts[i] = 0 ;
         cumulativeCounts [i] = 0 ;
    }
    
}

void countsingSort(){

    cout << "this is counting sort" << endl;

  entry* help = new entry[strlen(A[0])];
  setCountsArray();

  //Countsing number of each element's rank 0
  for(int i = 0; i < strlen(A[0]); i++){ //tested
    counts[L[i].nr[1]]++;
  }
  //Countsing cumulative counts of each element's rank 0
  for(int i = 1; i < 250; i++){//tested
    cumulativeCounts[i] = counts[i - 1 ] + cumulativeCounts[i - 1];
  }
  //Placing the nodes in right places
  for(int i = 0; i < strlen(A[0]); i++){
    help[cumulativeCounts[L[i].nr[1]]] = L[i];
    cumulativeCounts[L[i].nr[1]]++;
  }
  setCountsArray();
    cout << strlen(A[0]);
  //Countsing number of each element's rank 1
  for(int i = 0; i < strlen(A[0]); i++){//tested
    cout << help[i].nr[0] << " ";
    counts[help[i].nr[0]]++;
  }
  //Countsing cumulative counts of each element's rank 1
  for(int i = 1; i < 250; i++){
    cumulativeCounts[i] = counts[i - 1] + cumulativeCounts[i - 1];
  }
  //Replacing nodes based on rank 2 
  for(int i = 0; i < strlen(A[0]); i++){

    L[cumulativeCounts[help[i].nr[0]]] = help[i];
    cumulativeCounts[help[i].nr[0]]++;
  }
}

void buildsuff(int t){
    for (N = strlen(A[t]), i = 0; i < N; i++)
        P[t][0][i] = A[t][i] - 'a'; 
    for (stp = 1, cnt = 1; cnt >> 1 < N; stp++, cnt <<= 1) {
        for (i = 0; i < N; i++) {
            L[i].nr[0] = P[t][stp - 1][i];
            L[i].nr[1] = i + cnt < N ? P[t][stp - 1][i + cnt] : -1;
            L[i].p = i;
        }

        sort(L, L + N, cmp);

        for (i = 0; i < N; i++)
            P[t][stp][L[i].p] =
                    i > 0 && L[i].nr[0] == L[i - 1].nr[0] && L[i].nr[1] == L[i - 1].nr[1] ? P[t][stp][L[i - 1].p] : i;
    }
}

int lcp(int x, int y,int t){
    int k, ret = 0;
    if (x == y) return N - x;
    for (k = stp - 1; k >= 0 && x < N && y < N; k --)
        if (P[t][k][x] == P[t][k][y])
        x += 1 << k, y += 1 << k, ret += 1 << k;
    return ret;
}

void LPS(){
    char  reversed [strlen(A[0])]; 
    for(int x = 0; x < strlen(A[0]); x++)
    {
        reversed[x]=A[0][x];
    }
    char temp;
    int  cn1,cn2;
    cn2 = strlen(A[0])-1;
        for (cn1 = 0; cn1 < cn2; cn1++,cn2--)
    {
        temp = reversed[cn1];
        reversed[cn1] = reversed[cn2];
        reversed[cn2] = temp;
    }
    string reversedconcat ;
    string Astr , Revstr ;
    Astr = string (A[0]);
    Revstr = string (reversed);
    reversedconcat = Astr + Revstr ;
     strcpy(A[1], reversedconcat.c_str());  

   int LCPTEMP [strlen(A[1])] ;
    int highNum = 0;
    buildsuff(1);
 
       for(int q = 0 ; q < strlen(A[1])-1 ; q++){
                LCPTEMP[q] = lcp (L[q].p,L[q+1].p,1);
            }
          
        for(int i =0  ; i <;i++)
          cout << LCPTEMP[i] << endl;
  //maximum in suffix array
    int id=0;
    cout << "Str len A{1 }" << strlen(A[1]) <<endl;
    for (int m = 0 ; m < 12 ; m++)
    {
        if (LCPTEMP[m] > highNum)
            highNum = LCPTEMP[m];
            id = m;
        }
    cout << "MAX :" << highNum << " id : " << id << endl ;
    cout<< " lid : " << L[id].p <<endl;
    int cnt = 0;

    for ( int i = L[id].p  ; cnt < 5; i++ ){
        cout<<A[1][i] ;
        cnt++;
    }
}

int binarySearch(int l, int r, string key){
    if(r < l)
        return -1;
    int mid = (l + r) / 2;
    if(A[0].substr(L[mid]).stratsWith(key))
    return mid;
    if(key < A[0].substr(L[mid]))
        return binarySearch(l, mid - 1, key);
    return binarySearch(mid + 1, r, key);
}

void MLR(string str) {
    int j;
    string N = str + str;
   // cout << N;
    for (int i = 0; i < strlen(A[0]); i++) {
        A[1][i] = A[0][i];
        A[1][i + strlen(A[0])] = A[0][i];
    }
    buildsuff(1);
    for(int i = 0; i <= strlen(A[0]); i++){
        if(L[i].p <= strlen(A[0])){
            j = L[i].p;
            break;
        }
    } 
    for(int i = 0; i < strlen(A[0]); i++, j++){
        cout << A[1][j];
    }
}
void minLexRotation(string str) 
{ 
    int n = str.length(); 
  
    string arr[n]; 
  
    string concat = str + str; 
  
   
    for (int i = 0; i < n; i++) {
        arr[i] = concat.substr(i, n); 
    }
    sort(arr, arr+n); 
  
    cout << arr[0]; 
}
void UniqueSubstring(){
            int LCP [strlen(A[0])] ;
            int SumOFLcpellements = 0;
            int Sumofsubstrings = (strlen(A[0])*(strlen(A[0])+1))/2 ;
            for(int i = 0 ; i < strlen(A[0])-1 ; i++){
                LCP[i] = lcp (L[i].p,L[i+1].p,0);
            }
             for(int i=0 ; i < strlen(A[0]) ; i++){
                  SumOFLcpellements+=LCP[i];              
            }
             cout<< endl<<Sumofsubstrings - SumOFLcpellements;
}
int main(){

        gets(A[0]);
      
        buildsuff(0);
   int order;
   cin>>order;

    switch(order){
        case 2 :
        LPS();
        break;
        case 3 :
        UniqueSubstring();
        break;
        case 4 :
        MLR(A[0]);
        break;
        case 5 :
        buildsuff(1);
        break;
    }
    return 0 ; 


}