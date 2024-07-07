import React, { useState } from 'react';
import { useData } from '../lib/data-provider';

const ConfirmModal = () => {

    const { setConfirmModalOptions, confirmModalOptions, displayConfirmModal } = useData();

    const closeModal = () => {
        setConfirmModalOptions({
            ...confirmModalOptions,
            show: false,
        });

        confirmModalOptions.onCancel();
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center z-[999] bg-gray-300/50">
            <div className="bg-white rounded-lg text-black min-w-72 h-32 shadow-2xl mx-auto self-center px-10 py-4 flex flex-col justify-between">
                <span>{confirmModalOptions.message}</span>

                <div className="flex flex-row gap-4 w-full">
                    <button onClick={closeModal} className="w-full bg-transparent border border-gray-700 rounded hover:bg-gray-200">{confirmModalOptions.cancelText}</button>
                    <button onClick={confirmModalOptions.onConfirm} className="w-full bg-red-500 border border-gray-700 rounded hover:bg-red-700">{confirmModalOptions.confirmText}</button>
                </div>
            </div>
        </div>
    );
}

export default ConfirmModal;
