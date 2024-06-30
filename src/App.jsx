import React from 'react'
import AdminDashboard from './screens/admin-screen'
import UserScreen from './screens/user-screen'
import MembershipScreen from './screens/membership-screen'
import LoginScreen from './screens/login-screen'
import SignupScreen from './screens/signup-screen'
import LoadingScreen from './components/LoadingScreen'
import { useData } from './lib/data-provider'


function App() {

  const { isLoading, loadingMessage } = useData();

  return (
    <div id='root'>
        {/*<SignupScreen />*/}
      {isLoading && <LoadingScreen message={loadingMessage} />}
       {/*<LoginScreen />*/}
	{/*{ <AdminDashboard /> }*/}
       <UserScreen />
      {/* <MembershipScreen /> */}
    </div>
  )
}

export default App
