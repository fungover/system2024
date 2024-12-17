import {useEffect, useState} from "react";

interface UserPageProps {
    name: string;
    email: string;
}


function UsersPage(){
    const [users, setUsers] = useState<UserPageProps[]>([]);
    async function fetchAllUsers(): Promise<void> {


  let loot = await fetch('http://localhost:8080/graphql',{
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
  } )
        const payload = await loot.json();

        setUsers(payload.data.users);


    }

    useEffect(() => {
       fetchAllUsers()

    }, []);
    return(
        <section>
            <div>
                <h1>Our daily users!</h1>
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
                        <p className="animate-spin">Loading users...</p>
                    )}
                </div>


            </div>
        </section>
    )
}

export default UsersPage;