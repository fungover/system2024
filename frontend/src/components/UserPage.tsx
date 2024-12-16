import {useEffect, useState} from "react";

interface UserPageProps {
    name: string;
    email: string;
}


function UsersPage(){
    const [users, setUsers] = useState<UserPageProps[]>([]);
    async function fetchAllUsers(): Promise<UserPageProps[]> {
        const listOfUsers = [];

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
    console.log(users)
        const payload = await loot.json();
  listOfUsers.push(payload.data);
  return listOfUsers;

    }

    useEffect(() => {
        const getAllUsers = async ()=>{
            const listOfUsers = await fetchAllUsers();
            setUsers(listOfUsers);
        }

        getAllUsers()

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
                        <p>Loading users...</p>
                    )}
                </div>


            </div>
        </section>
    )
}

export default UsersPage;