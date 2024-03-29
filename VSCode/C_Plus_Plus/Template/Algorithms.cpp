#include <iostream>
#include <array>
#include <vector>
#include <climits>

using namespace std;

template<typename T>
void print(const vector<T>& arr)
{
	for(const T& e : arr)
		cout << e << ',';
	cout << endl;
}

template<typename T>
class BubbleSort
{
public:
	static void sort(vector<T>& arr)
	{
		if(arr.size() <= 1)	return;

		for(int i = 0; i < arr.size(); ++i)
		{
			for(int j = 1; j < arr.size() - i; ++j)
			{
				if(arr[j] < arr[j - 1])
					swap(arr[j], arr[j - 1]);
			}
		}
	}
};

template<typename T>
class SelectionSort
{
public:
	static void sort(vector<T>& arr)	
	{
		if(arr.size() <= 1)	return;

		int minIndex = 0;
		for(int i = 0; i < arr.size() - 1; ++i)
		{
			minIndex = i;
			for(int j = i + 1; j < arr.size(); ++j)
			{
				if(arr[j] < arr[minIndex])
					minIndex = j;
			}
			swap(arr[i], arr[minIndex]);
		}
	}
};

template<typename T>
class InsertionSort
{
public:
	static void sort(vector<T>& arr)
	{
		for(size_t i = 1; i < arr.size(); ++i){
			T tmpValue = arr[i];
			int prev = i - 1;
			while((prev >= 0) && tmpValue < arr[prev]){
				arr[prev + 1] = arr[prev];
				--prev;
			}
			arr[prev + 1] = tmpValue;
		}
	}
};

template<typename T>
class QuickSort
{
public:
	static void sort(vector<T>& arr)
	{
		if(arr.size() <= 1)
			return;
		
		quickSort(arr, 0, arr.size() - 1);
	}

private:
	static void quickSort(vector<T>& arr, int start, int end)
	{
		if(start >= end)
			return;

		int pivot = start;
		int left = pivot + 1;
		int right = end;
		
		while(left <= right)
		{
			while((arr[left] <= arr[pivot]) && (left <= end)) ++left;
			while((arr[pivot] < arr[right]) && (right > start)) --right;

			if(left > right) 
				swap(arr[pivot], arr[right]);
			else
				swap(arr[left], arr[right]);
		}
		// for(int i = start; i <= end; ++i)
		// 	cout << arr[i] << ", ";
		// cout << endl;

		quickSort(arr, start, right - 1);
		quickSort(arr, right + 1, end);
	}
};

template<typename T>
class MergeSort
{
public:
	static void sort(vector<T>& arr)
	{
		if(arr.size() <= 1)
			return;
		
		mergeSort(arr, 0, arr.size() - 1);
	}

private:
	static void mergeSort(vector<T>& arr, int left, int right)
	{
		if(left < right)
		{
			int mid = (left + right) / 2;

			mergeSort(arr, left, mid);
			mergeSort(arr, mid + 1, right);
			merge(arr, left, mid, right);
		}
	}

	static void merge(vector<T>& arr, int left, int mid, int right)
	{
		vector<T> L(arr.begin() + left, arr.begin() + mid + 1);
		vector<T> R(arr.begin() + mid + 1, arr.begin() + right + 1);

		int i = 0, j = 0, k = left;
		int ll = L.size(), rl = R.size();
		while(i < ll && j < rl)
		{
			if(L[i] <= R[j])
				arr[k] = L[i++];
			else
				arr[k] = R[j++];
			k++;
		}

		while(i < ll) arr[k++] = L[i++];
		while(j < rl) arr[k++] = R[j++];
	}
};

template<typename T>
class HeapSort
{
public:
	static void sort(vector<T>& arr)
	{	
		int n = arr.size();
		
		cout << "Max Heap 초기화" << endl;
		for(int i = n / 2 - 1; i >= 0; --i)
			heapify(arr, n, i);

		cout << "추출 연산" << endl;
		for(int i = n - 1; i > 0; --i)
		{
			swap(arr[0], arr[i]);
			heapify(arr, i, 0);
		}
	}

private:
	static void heapify(vector<T>& arr, int n, int i)
	{
		int parent = i;
		int lChild = i * 2 + 1;
		int rChild = i * 2 + 2;

		// 좌측
		if(lChild < n && arr[parent] < arr[lChild])
			parent = lChild;
		// 우측
		if(rChild < n && arr[parent] < arr[rChild])
			parent = rChild;

		if(i != parent)
		{
			swap(arr[parent], arr[i]);
			print(arr);
			heapify(arr, n, parent);
		}
	}

};

// Reference : https://soobarkbar.tistory.com/101
class CountingSort
{
public:
	static void sort(vector<int>& arr)
	{
		vector<int> ret(arr.size(), 0);
		vector<int> counting(100000, 0);
		
		for(int a : arr)
			counting[a]++;
		

		for(int i = 1; i < 100000; ++i)
			counting[i] += counting[i - 1];

		for(int i = 0; i < arr.size(); ++i)
		{
			ret[--counting[arr[i]]] = arr[i];
		}
		arr = ret;
	}
};

class RadixSort
{
public:
	static void sort(vector<int>& arr)
	{
		int n = sizeof(arr) / sizeof(arr[0]);
		int nMax = getMax(arr);

		for(int exp = 1; nMax / exp > 0; exp *= 10)
			countSort(arr, n, exp);
	}
	
private:
	static int getMax(const vector<int>& arr)
	{
		int ret = INT_MIN;
		for(const int a : arr)
		{
			if(a > ret)
				ret = a;
		}
		return ret;
	}

	static void countSort(vector<int>& arr, int n, int exp)
	{
		vector<int> buffer(n);
		vector<int> counting(getMax(arr) + 1);

		for(int i = 0; i < arr.size(); ++i)
			++counting[arr[i]];

		for(int i = 1; i < getMax(arr) + 1; ++i)
			counting[i] += counting[i - 1];

		for(int i = 0; i < n; ++i)
			buffer[--counting[(arr[i] / exp) % 10]] = arr[i];

		for(int i = 0; i < n; ++i)
			arr[i] = buffer[i];
	}
};

int main()
{
	//vector<int> arr = { 8, 4, 11, 2, 3, 4, 9, 1, 6, 5};
	//vector<int> arr = { 1, 2, 3, 4, 5 };
	vector<int> arr = { 8, 4, 11, 2, 3, 4, 9, 1, 6, 5, 101, 20222, 392, 13, 42};
	InsertionSort<int>::sort(arr);
	print(arr);

	return 0;
}