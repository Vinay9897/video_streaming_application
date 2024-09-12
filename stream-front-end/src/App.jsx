import { useState } from 'react'
import './App.css'
import VideoUpload from "./components/VideoUpload";
import { Toaster } from 'react-hot-toast';

function App() {
  const [count, setCount] = useState(0);

  const [videoId, setVideoId] = useState("77a67378-89ad-41b7-a98d-938cacb754bc");
  debugger
  return (
    <>
      <Toaster />
      <div className="flex flex-col items-center space-y-9 justify-center py-9">
        <h1 className=" text-2xl font-bold text-gray-700 dark:text-gray-100">
          Welcome to Video Streaming Application
        </h1>
        <div className='flex w-full justify-around'>
          <div>
            debugger
            {/* <h1 className="text-white">Playing Video</h1> */}
            <video style={{ width: 500, height: 440, }} src={`http://localhost:8080/app/stream/{videoId}`} controls />
          </div>
          <VideoUpload />
        </div>
      </div>
    </>
  );
}

export default App;
