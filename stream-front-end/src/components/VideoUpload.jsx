import React, { useState } from "react";
import videoLogo from "../assets/upload.png";
import { Alert, Card, FileInput, Label, Progress, Textarea, TextInput } from "flowbite-react";
import { Button } from "flowbite-react";
import axios from "axios";
import toast from "react-hot-toast";

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

        // console.log(meta.description);
        // console.log(meta.title)
    }

    function handleFileChange(event) {
        setSelectedFile(event.target.files[0])
    }

    function handleForm(formEvent) {
        formEvent.preventDefault();
        if (!selectedFile) {
            alert("Select File!!");
            return;
        }

        const formData = new FormData();
        formData.append("file", selectedFile);
        formData.append("title", meta.title);
        formData.append("description", meta.description);
        saveVideoToServer(formData);
        // resetForm();
    }

    // reset form

    function resetForm() {

        setMeta({
            title: "",
            description: ""
        });
        setSelectedFile(null);
        setUploading(false);

    }

    //submit file to server

    async function saveVideoToServer(formData) {
        setUploading(true);
        try {
            let response = await axios.post(`http://localhost:8080/app/videos`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },

                onUploadProgress: (progressEvent) => {
                    const total = progressEvent.total;

                    const currentProgress = Math.round(
                        (progressEvent.loaded * 100) / total
                    );
                    setProgress(currentProgress);
                }
            });
            setMessage("File uploaded successfully!");
            setUploading(false);
            console.log(response);
            toast.success("File uploaded successfully!");
            resetForm();
        }
        catch (error) {
            console.log(error);
            console.log("error in uploading file");
            setUploading(false);
            toast.error("File uploaded successfully!");
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

                    {/* title */}
                    <div className="mb-3">
                        <div className="pb-2 block">
                            <Label htmlFor="file-upload" value="Upload file" />
                        </div>
                        <TextInput value={meta.title} onChange={formFieldChange} name="title" id="file-upload" placeholder="Enter title" />
                    </div>

                    {/* descreption */}
                    <div className="mb-3 max-w-md">
                        <div className="mb-2 block">
                            <Label htmlFor="comment" value="Video Description" />
                        </div>
                        <Textarea value={meta.description} onChange={formFieldChange} name="description" id="comment" placeholder="write a video description" required rows={4} />
                    </div>

                    {/* file */}
                    <div className="flex justify-center  items-center space-x-6">
                        <div className="shrink-0">
                            <img className="h-16 w-16 object-cover rounded-sm" src={videoLogo} alt="Current profile photo" />
                        </div>

                        <label className="block">
                            <span className="sr-only">Choose profile photo</span>
                            <input
                                ref={selectedFile}
                                name="file" type="file"
                                onChange={handleFileChange}
                                className="block w-full text-sm text-slate-500
                            file:mr-4 file:py-2 file:px-4
                            file:rounded-full file:border-0
                            file:text-sm file:font-semibold
                             file:bg-violet-50 file:text-violet-700
                             hover:file:bg-violet-100"/>
                        </label>
                    </div>
                    <div className="my-4 " >
                        {uploading && <Progress progress={progress} textLabel="Uploading" size="lg" labelProgress labelText style={{ padding: '2px' }} />}
                    </div>

                    <div className="my-4" >
                        {message && <Alert color="success" onDismiss={() => {
                            // setSelectedFile("");
                            setMessage("");
                        }} >
                            <span className="font-medium border">File Uploading Successful</span>
                        </Alert>}
                    </div>

                    <div className="flex justify-center">
                        <Button type="submit">
                            Upload Video</Button>
                    </div>
                </form>
            </div>

        </Card >
    </div >
}

export default VideoUpload;