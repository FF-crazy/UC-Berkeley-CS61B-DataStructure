import java.util.Arrays;

public class Sort {
//    private int[] array;
//
//    public Sort() {
//        array = new int[10];
//        Random random = new Random(1024);
//        for (int i = 0; i < 10; i++) {
//            array[i] = random.nextInt(100);
//        }
//    }

  public static int[] bubbleSort(int[] arr) {
    int[] res = Arrays.copyOf(arr, arr.length);
    for (int i = 0; i < arr.length - 1; i++) {
      boolean swaped = false;
      for (int j = 0; j < arr.length - 1 - i; j++) {
        if (res[j] > res[j + 1]) {
          res[j] = res[j] + res[j + 1];
          res[j + 1] = res[j] - res[j + 1];
          res[j] = res[j] - res[j + 1];
          swaped = true;
        }
      }
      if (!swaped) {
        return res;
      }
    }
    return res;
  }

  public static int[] selectSort(int[] arr) {
    int[] res = Arrays.copyOf(arr, arr.length);
    for (int i = 0; i < res.length - 1; i++) {
      int min = i;
      for (int j = i + 1; j < res.length; j++) {
        if (res[j] < res[min]) {
          min = j;
        }
      }
     swap(res, min, i);
    }
    return res;
  }

  public static int[] insertSort(int[] arr) {
    int[] res = Arrays.copyOf(arr, arr.length);
    for (int i = 1; i < res.length; i++) {
      int tmp = res[i];
      int j = i;
      for (; j > 0 && tmp < res[j - 1]; j--) {
        res[j] = res[j - 1];
      }
      res[j] = tmp;
    }
    return res;
  }

  public static int[] heapSort(int[] arr) {
    int[] res = Arrays.copyOf(arr, arr.length);
    int len = res.length;
    for (int i = len / 2 - 1; i > -1; i--) {
      heapify(res, i, len);
    }
    for (int i = len - 1; i > 0; i--) {
      swap(res, 0, i);
      heapify(res, 0, --len);
    }
    return res;
  }

  private static void heapify(int[] arr, int i, int len) {
    int left = 2 * i + 1;
    int right = 2 * i + 2;
    int largest = i;
    if (left < len && arr[left] > arr[largest]) {
      largest = left;
    }
    if (right < len && arr[right] > arr[largest]) {
      largest = right;
    }
    if (i != largest) {
      swap(arr, largest, i);
      heapify(arr, largest, len);
    }
  }

  public static int[] mergeSort(int[] arr) {
    int[] res = Arrays.copyOf(arr, arr.length);
    int[] temp = Arrays.copyOf(res, res.length);
    mSort(res, temp, 0, res.length - 1);
    return res;
  }

  private static void mSort(int[] arr, int[] temp, int left, int right) {
    if (left < right) {
      int mid = (left + right) / 2;
      mSort(arr, temp, left, mid);
      mSort(arr, temp, mid + 1, right);
      merge(arr, temp, left, right);
    }
  }

  private static void merge(int[] arr,int[] temp, int left, int right) {
    int i = left;
    int j = (left + right) / 2 + 1;
    int pos = left;
    while (i <= (left + right) / 2 && j <= right) {
      if (arr[i] <= arr[j]) {
        temp[pos++] = arr[i++];
      } else {
        temp[pos++] = arr[j++];
      }
    }
    while (i <= (left + right) / 2) {
      temp[pos++] = arr[i++];
    }
    while (j <= right) {
      temp[pos++] = arr[j++];
    }
    for (i = left; i <= right; i++) {
      arr[i] = temp[i];
    }
  }

  public static int[] quickSort(int[] arr) {
    int[] res = Arrays.copyOf(arr, arr.length);
    quickSort(res, 0, res.length - 1);
    return res;
  }

  private static void quickSort(int[] arr, int left, int right) {
    if (left < right) {
      int pivot = partition(arr, left, right);
      quickSort(arr, left, pivot - 1);
      quickSort(arr, pivot + 1, right);
    }
  }

  private static int partition(int[] arr, int left, int right) {
    int i = left + 1;
    int j = right;
    while (true) {
      while (i <= right  && arr[i] < arr[left]) {
        i++;
      }
      while (j >= left + 1 && arr[j] >= arr[left]) {
        j--;
      }
      if (i >= j) {
        break;
      }
      swap(arr, i, j);
    }
    swap(arr, j, left);
    return j;
  }
  private static void swap(int[] arr, int i, int j) {
    if (i != j) {
      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
    }
  }
}

