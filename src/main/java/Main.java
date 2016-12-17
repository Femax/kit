import Jama.Matrix;

/**
 * Created by femax on 10.12.2016.
 */
public class Main {



    public static void main(String[] args) {

        double scopeX = 1;
        double scopeY = 1;
        double h = 0.125;
        int n = (int) Math.round(1 / h);
        double nu = 1.0006 * Math.pow(10, -6);
        double ro = 1000;
        double[][] X = new double[n * n][n * n];
        double[][] Y = new double[n * n][n * n];
        double[] kot=new double[n * n];


        /**Заполняем матрицу коэфициентов*/
        for (int i = 0; i < n * n; i++) {
            int hn = i - n;
            int hp = i + n;
            int xn = i - 1;
            int xp = i + 1;
            if (i < n || i >= (n * n - n)) {
                X[i][i] = 1;
            } else {
                X[i][hp] = 1;
                X[i][hn] = 1;
                X[i][xp] = 1;
                X[i][xn] = 1;
                X[i][i] = 4;
            }
        }

        kot[10] =0.03*0.03/40;

        for (int i = 0; i < n * n; i++) {
            int hn = i - n;
            int hp = i + n;
            int xn = i - 1;
            int xp = i + 1;
            if ((i < n) || (i >= (n * n - n))) {
                Y[i][i] = 1;
            } else {
                Y[i][hp] = 1;
                Y[i][hn] = 1;
                Y[i][xp] = 1;
                Y[i][xn] = 1;
                Y[i][i] = 4;
            }
        }
//
//        System.out.println("x");
//        for (int i = n; i < n * n - n; i++) {
//            for (int j = n; j < n * n - n; j++) {
//                System.out.print(X[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println("y");
//        for (int i = n; i < n * n - n; i++) {
//            for (int j = n; j < n * n - n; j++) {
//                System.out.print(X[i][j] + " ");
//            }
//            System.out.println();
//        }

      /**Заполняем матрицу из двумерного массива*/
        Matrix matrixX = new Matrix(X);
        /**Значения правой части уравнения*/
        Matrix freedomElementsX;
        double[] rhsX = new double[n*n];
        for(int i=0;i<n*n;i++) {
            rhsX[i] = (1./(ro*nu));
        }
        freedomElementsX= new Matrix(rhsX,n*n);
        /**Находит решение и представляет его в виде вектора*/
        Matrix resx = matrixX.solve(freedomElementsX);

        /**Аналогично с спервым*/
        Matrix matrixY = new Matrix(Y);
        Matrix freedomElementsY;
        double[] rhsY = new double[n*n];
        for(int i=0;i<n*n;i++) {
            rhsY[i] = (1./(nu))+kot[i];
        }
        freedomElementsY= new Matrix(rhsY,n*n);
        Matrix resy = matrixX.solve(freedomElementsY);


        double[] absoluteValue = new double[n];
        double[] angle = new double[n];
        for(int i = 0 ; i<n ; i++){
            double vx = resx.get(i,0);
            double vy = resy.get(i,0);
            absoluteValue[i] = Math.sqrt(vx*vx+vy*vy);
            angle[i] = Math.atan(vx/vy);
        }
        for(int i = 0 ; i<n ; i++){
            System.out.println(angle[i]);

        }

        for(int i = 0 ; i<n ; i++){
            System.out.println(absoluteValue[i]);
        }

    }


//Example
//    double[][] array = {{1.,0,0},{0,1,0},{0,0,1}};
//        Matrix A = new Matrix(array);
//        Matrix b = Matrix.random(3,1);
//        Matrix x = A.solve(b);
}
