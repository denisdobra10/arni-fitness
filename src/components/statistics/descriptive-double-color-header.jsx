import React from 'react'

function DescriptiveDoubleColorHeader({ first = "First", second = "Second", description, className }) {
    return (
        <div className={`flex flex-col gap-3 justify-center items-center ${className}`}>
            <div className='text-4xl font-semibold uppercase'>
                <h2 className='text-primary'>{first} <span className=' text-gray-600'>{second}</span></h2>
            </div>
            {description && <span>{description}</span>}
        </div>

    )
}

export default DescriptiveDoubleColorHeader