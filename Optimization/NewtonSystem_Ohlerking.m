function [x,err,iter] = NewtonSystem_Ohlerking(x, tol, N)

i = 0;% iteration counter
err = tol + 1;% err needs an initial value

while i < N && err > tol% stopping criteria
    
    p =  h(x) \ g(x) ;% newton's method
    
    x1 = x - p';
    err = norm(p');% update stopping criteria
    i = 1 + i;% iterate counter
    x = x1;
    
end
x = x1;% updates x so that answer will print out as latest x1 value

end

function y = f(x)% for debugging

y = x(1)^2 + x(2)^2 - sin(x(1)) * sin(x(2)) - x(1) + 3 * x(2);

end

function y = g(x)% gradient of f(x)
% function g
y = [ 2 * x(1) - cos(x(1)) * (sin(x(2))) - 1 ; 2 * x(2) - sin(x(1)) * (cos(x(2))) + 3 ];

end

function y = h(x)% hessian of f(x)
% function h
y = [ 2 + sin(x(1)) * (sin(x(2))) , -cos(x(1)) * (cos(x(2))); - cos(x(1)) * (cos(x(2))) , 2 + sin(x(1)) * (sin(x(2))) ];

end
