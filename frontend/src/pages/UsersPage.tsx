import { useEffect, useState } from "react";

interface UserPageProps {
    name: string;
    email: string;
}

function UsersPage() {
    const [users, setUsers] = useState<UserPageProps[]>([]);

    async function fetchAllUsers(): Promise<void> {
        try {
            const response = await fetch('http://localhost:8080/graphql', {
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

            const payload = await response.json();
            setUsers(payload.data.users);
        } catch (error) {
            console.error("Error fetching users:", error);
        }
    }

    useEffect(() => {
        fetchAllUsers();
    }, []);

    return (
        <section className="">
            <div>
                <h1 className="text-pink-500">Our daily users!</h1>
                <div>
                    {users.length > 0 ? (
                        <ul>
                            {users.map((user, index) => (
                                <li key={index}>
                                    <strong>{user.name}</strong>: {user.email}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p className="animate-spin">No one uses our application =(</p>
                    )}
                </div>
            </div>
        </section>
    );
}

export default UsersPage;
