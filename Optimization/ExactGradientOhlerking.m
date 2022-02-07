function [x,min,err,iter] = ExactGradientOhlerking(x0,tol,N,A)
    x = x0;
    i = 0;
    p = GD(x, A);
    err = 1;
    while err > tol && i < N
        x = x + EM(x,p, A) * p;
        p = GD(x, A);
        err = norm(p);
        i = i + 1;
    end
    i

end

function alph = EM(x,p, A)
    a = 0; c = 1; b = 2;
    u = f(x,p,a,A);
    v = f(x,p,c,A);
    w = f(x,p,b,A);
    while v>=w
        a = c; c = b; b = 2 * b; v = w; 
        w = f(x,p,b,A);
    end
    alph=fminbnd(@(alph) f(x,p,alph,A),a,b)
end

function p = GD(x, A)
    p =-A*x;
end

function y = f(x, p, alph, A)
    z = x + alph * p ;
    y = .5 * z' * A * z;
end
