package xyz.fbeye.datatype;

public class Matrix {

    private static final double EPS = 1e-16;

    private final double[][] var;
    private final int rows;
    private final int cols;


    public Matrix(int rows, int cols){
        var = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            var[i] = new double[cols];
        }
        this.rows = rows;
        this.cols = cols;

    }

    public static Matrix Multiply(Matrix a, Matrix b) {

        if( a.cols != b.rows )
            return null;

        Matrix m;
        int row = a.rows;
        int col = b.cols;

        m = new Matrix(row, col);
        for(int j = 0 ; j < row ; j++ ){
            for(int i = 0 ; i < col ; i++ ){
                for(int k = 0 ; k < a.cols ; k++ ){
                    m.var[j][i] += a.var[j][k]*b.var[k][i];
                }
            }
        }
        return m;
    }

    public void setData(int row, int col, float data){
        var[row][col] = data;
    }

    public void setData(int row, int col, double data){
        var[row][col] = data;
    }

    public double getData(int row, int col){
        return var[row][col];
    }

    public static Matrix inverse(Matrix src){

        if(src.rows != src.cols){
            return null;
        }

        Matrix inv = new Matrix(src.rows, src.cols);
        Matrix n = new Matrix(src.rows, src.cols * 2);
        int iter = src.rows;
        double v;


        for (int i = 0; i < iter; i++) {
            System.arraycopy(src.var[i], 0, n.var[i], 0, iter);
        }

        for (int i = 0; i < iter; i++) {
            n.var[i][i+iter] = 1.0;
        }

        int maxKey;
        for(int i = 0; i<iter; ++i){

            maxKey = i;

            for(int j = i+1; j<iter; ++j){
                if(n.getData(j,i) > n.getData(maxKey,i)){
                    maxKey = j;
                }
            }

            if(maxKey != i){
                for(int j = 0; j < iter*2; ++j){
                    double tmp = n.var[i][j];
                    n.var[i][j] = n.var[maxKey][j];
                    n.var[maxKey][j] = tmp;
                }
            }

            v = n.var[i][i];
            for(int j = i+1 ; j < iter*2 ; j++ )
                n.var[i][j] /= v + EPS;

            for(int j = i+1 ; j < iter ; j++ ){
                v = n.var[j][i];
                n.var[j][i] = 0.0;
                for(int k = i+1 ; k < iter*2 ; k++ ){
                    n.var[j][k] -= n.var[i][k]*v;
                }
            }

        }

        for(int i = iter-2 ; i >= 0 ; i-- ){

            for(int j = i ; j >= 0 ; j-- ){
                v = n.var[j][i+1];
                for(int k = 0 ; k < iter*2 ; k++ ){
                    n.var[j][k] -= n.var[i+1][k]*v;
                }
            }
        }

        for(int j = 0 ; j < iter ; j++ )
            System.arraycopy(n.var[j], iter, inv.var[j], 0, iter);

        return inv;
    }



}
