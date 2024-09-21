import { useState } from 'react'
import './App.css'
import VideoUpload from "./components/VideoUpload";
import { Toaster } from 'react-hot-toast';

function App() {
  const [count, setCount] = useState(0);
  const [videoId, setVideoId] = useState("88a78691-cac6-42aa-b570-f40b10ac1196");

  return (
    <>
      <Toaster />
      <div className="flex flex-col items-center space-y-9 justify-center py-9">
        <h1 className=" text-2xl font-bold text-gray-700 dark:text-gray-100">
          Welcome to Video Streaming Application
        </h1>
        <div className=' flex w-full justify-around'>
          <div className=''>
            {/* <video style={{ width: 600, height: 400 }} src={`http://localhost:8080/app/stream/range/${videoId}`} controls>
            </video> */}
            <VideoUpload src={``}
            ></VideoUpload>
          </div>
          <div>
            <VideoUpload />
          </div>
        </div>
      </div>
    </>
  );
}

export default App;
