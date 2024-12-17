import { useEffect, useState } from "react";

interface UserPageProps {
    name: string;
    email: string;
}

function UsersPage() {
    const [users, setUsers] = useState<UserPageProps[]>([]);
    const apiUrl = import.meta.env.VITE_USERS_URL;
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    async function fetchAllUsers(): Promise<void> {
        setIsLoading(true);
        setError(null);
        try {
            const response = await fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    query: `{
                        users {
                            name
                            email 
                        }
                    }`
                })
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const payload = await response.json();
            if (payload.errors) {
                throw new Error(payload.errors[0].message);
            }

            setUsers(payload.data.users);
        } catch (err) {
            const errorMessage = err instanceof Error ? err.message : 'An unknown error occurred';
            setError(errorMessage);
            console.error("Error fetching users:", errorMessage);
        } finally {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        fetchAllUsers();
    }, []);

    return (
        <section >
            <div>
                <h1 >Our daily users!</h1>
                {isLoading ? (
                    <p className="animate-bounce">Loading users...</p>
                ) : error ? (
                    <p className="text-red-500">Error: {error} users</p>
                ) : users.length > 0 ? (
                    <ul>
                        {users.map((user) => (
                            <li key={user.email}>
                                <strong>{user.name}</strong>: {user.email}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No one uses our application =(</p>
                )}
            </div>
        </section>
    );
}

export default UsersPage;
