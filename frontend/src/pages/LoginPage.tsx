import React from 'react';

interface LoginPageProps {
    onLoginSuccess?: () => void;
    onLoginError?: (error: Error) => void;
}

const LoginPage: React.FC<LoginPageProps> = () => {
    return (
        <div className="min-h-screen flex flex-col items-center justify-center bg-gray-50">
            <h1 className="text-3xl font-bold mb-6 text-gray-800">Welcome to System2024!</h1>
            <p className="mb-8 text-gray-600">Sign in with your GitHub account to continue</p>
            <a
                href="/oauth2/authorization/github"
                className="inline-block bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700 transition text-center"
                role="button"
                aria-label="Sign in with GitHub"
            >
                Sign in with GitHub
            </a>
        </div>
    );
};

export default LoginPage;
