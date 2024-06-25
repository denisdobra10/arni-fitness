import React from 'react'

const LoadingScreen = ({ message = "Loading..." }) => {
    return (
        <>
            <div className="fixed top-0 flex flex-col z-50 w-full h-full justify-center items-center">
                <div className="rounded-full border-2 border-primary border-r-0 h-16 w-16 animate-spin"></div>
                <p className="text-white text-2xl mt-4">{message}</p>
            </div>
            <div className="fixed flex items-center justify-center top-0 bg-black opacity-80 h-full w-full z-40"></div>
        </>

    )
}

export default LoadingScreen