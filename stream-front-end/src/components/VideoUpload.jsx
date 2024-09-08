import React, { useState } from "react";
import videoLogo from "../assets/upload.png";
import { Card, FileInput, Label, Textarea, TextInput } from "flowbite-react";
import { Button } from "flowbite-react";


function VideoUpload() {

    const [selectedFile, setSelectedFile] = useState(null);
    const [progress, setProgress] = useState(0);
    const [meta, setMeta] = useState({
        title: "",
        description: ""
    });

    const [uploading, setUploading] = useState(false);
    const [message, setMessage] = useState("");

    function formFieldChange(event) {
        setMeta({
            ...meta, [event.target.name]: event.target.value
        });
    }

    function handleFileChange(event) {
        setSelectedFile(event.target.files[0])
        console.log(event);
    }

    function handleForm(formEvent) {
        if (!selectedFile) {
            alert("Select File!!");
            return;
        }
    }

    //submit file to server

    async function saveVideoToServer(video, videoMetaData) {
        setUploading(true);

        try {
            let response = await axios.post(`https://localhost:8080/videos`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },

                onUploadProgress: (progressEvent) => {
                    const total = progressEvent.total;
                    const currentProgress = Math.round(
                        (progressEvent.loaded * 100) / total
                    );
                    // setProgress(currentProgress);
                }

            });
            setMessage("File uploaded successfully!");
            console.log(response.data);
        }
        catch (error) {

        }
    }



    return <div className="text-white">
        <Card className="flex justify-center ">
            <div>
                <h1 className="font-semibold text-center">
                    Upload Video
                </h1>

                <form
                    noValidate
                    onSubmit={handleForm}
                    className="items-center my-2">

                    <div className="mb-3">
                        <div className="pb-2 block">
                            <Label htmlFor="file-upload" value="Upload file" />
                        </div>
                        <TextInput onChange={formFieldChange} name="title" id="file-upload" placeholder="Enter title" />
                    </div>

                    {/* textarea */}
                    <div className="mb-3 max-w-md">
                        <div className="mb-2 block">
                            <Label htmlFor="comment" value="Video Description" />
                        </div>
                        <Textarea onChange={formFieldChange} name="description" id="comment" placeholder="write a video description" required rows={4} />
                    </div>

                    <div className="flex justify-center  items-center space-x-6">
                        <div className="shrink-0">
                            <img className="h-16 w-16 object-cover rounded-sm" src={videoLogo} alt="Current profile photo" />
                        </div>

                        <label className="block">
                            <span className="sr-only">Choose profile photo</span>
                            <input name="file" type="file"
                                onChange={handleFileChange}
                                className="block w-full text-sm text-slate-500
                            file:mr-4 file:py-2 file:px-4
                            file:rounded-full file:border-0
                            file:text-sm file:font-semibold
                             file:bg-violet-50 file:text-violet-700
                             hover:file:bg-violet-100"/>
                        </label>
                    </div>

                    <div className="flex justify-center">
                        <Button type="submit">
                            Upload Video</Button>
                    </div>
                </form>
            </div>

        </Card>
    </div>
}

export default VideoUpload;